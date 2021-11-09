package com.example.webProject.controllers;

import com.example.webProject.entity.AppUser;
import com.example.webProject.models.Client;
import com.example.webProject.models.ClientItem;
import com.example.webProject.models.InfoItems;
import com.example.webProject.models.Item;
import com.example.webProject.repository.*;
import com.example.webProject.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    UserRoleRepository userRoleRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ClientItemRepository clientItemRepository;

    @RequestMapping(value = {"/", "/welcome"}, method = RequestMethod.GET)
    public String welcomePage(Model model) {
        model.addAttribute("title", "Welcome");
        model.addAttribute("message", "This is welcome page!");

        return "welcomePage";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage(Model model) {

        return "loginPage";
    }

    @RequestMapping(value = "/logoutSuccessful", method = RequestMethod.GET)
    public String logoutSuccessfulPage(Model model) {
        model.addAttribute("title", "Logout");

        return "logoutSuccessfulPage";
    }

    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public String accessDenied(Model model, Principal principal) {
        if (principal != null) {
            User loginedUser = (User) ((Authentication) principal).getPrincipal();

            String userInfo = WebUtils.toString(loginedUser);

            model.addAttribute("userInfo", userInfo);

            String message = "Hi " + principal.getName() //
                    + "<br> You do not have permission to access this page!";
            model.addAttribute("message", message);
        }
        return "403Page";
    }

    @RequestMapping(value = "/manager", method = RequestMethod.GET)
    public String adminPage(Model model, Principal principal) {

//        User loginedUser = (User) ((Authentication) principal).getPrincipal();

//        List <Client> clients = (List<Client>) clientRepository.findAll(Sort.by(Sort.Direction.ASC, "client"));
//        model.addAttribute("clientRepository", clients);

//        List <Item> items = (List<Item>) itemRepository.findAll();
//        model.addAttribute("itemRepository", items);
//        model.addAttribute("userInfo", items);

        return "generalPage";
    }

    @RequestMapping(value = "/employee", method = RequestMethod.GET)
    public String employeePage(Model model, Principal principal) {

        String userName = principal.getName();
        System.out.println("User Name: " + userName);

//        List <Client> clients = (List<Client>) clientRepository.findAll();
//        Client client = clients.get(0);
//        System.out.println(clientItemRepository.getItemsByClient(client));
//        List <ClientItem> clientItems = (List<ClientItem>) clientItemRepository.getItemsByClient(client);
//        InfoItems infoItems = new InfoItems();
//        infoItems.setClient(clientItems.get(0).getClient());
//        List <Item> itemss = new ArrayList<>();
//        for (ClientItem clientItem: clientItems) {
//            if (clientItem.getItem().getDelivery_type().equals("delivery to client"));
//            itemss.add(clientItem.getItem());
//        }
//        infoItems.setItems(itemss);

        List<Item> items = (List<Item>) itemRepository.findAll();
        model.addAttribute("itemRepository", items);

        return "employeePage";
    }

    @RequestMapping(value = "/infoDE", method = RequestMethod.GET)
    public String infoDilEmp(Model model, Principal principal) {

        String userName = principal.getName();
        System.out.println("User Name: " + userName);


        return "infoDeliveryEmployee";
    }

    @PostMapping("/infoDE")
    public String deInfo(@RequestParam String id, Model model) {
//          Client client = clientRepository.getClientByClient(name);
        Item item = itemRepository.findById(new Long(id)).get();
//           ClientItem clientItem = clientItemRepository.()
        clientItemRepository.deleteById(clientItemRepository.getClientItemByItem(item).getId());

        return "infoDeliveryEmployee";
    }


    @RequestMapping(value = "/userInfo", method = RequestMethod.GET)
    public String userInfo(Model model, Principal principal) {

        String userName = principal.getName();
        System.out.println("User Name: " + userName);

        User loginedUser = (User) ((Authentication) principal).getPrincipal();
        AppUser appUser = userRepository.getAppUserByUserName(loginedUser.getUsername());
        List<ClientItem> clientItems = (List<ClientItem>) clientItemRepository.getItemsByClient(clientRepository.findById(appUser.getUserId()).get());
        List<Item> items = new ArrayList<>();
        for (ClientItem ci : clientItems) {
            items.add(ci.getItem());
        }

        model.addAttribute("cLIt", clientRepository.findById(appUser.getUserId()).get().getClient());

        model.addAttribute("userInfo", items);
        return "userInfoPage";
    }

    @PostMapping("/infoDU")
    public String updateData(@RequestParam String id, @RequestParam String delivery_type, Model model, Principal principal) {
        // Находим нужную запись по ID
        Item item = itemRepository.findById(new Long(id)).get();
        item.setDelivery_type(delivery_type);
        itemRepository.save(item);
        User loginedUser = (User) ((Authentication) principal).getPrincipal();
        AppUser appUser = userRepository.getAppUserByUserName(loginedUser.getUsername());

        List<ClientItem> clientItems = (List<ClientItem>) clientItemRepository.getItemsByClient(clientRepository.findById(appUser.getUserId()).get());
        List<Item> items = new ArrayList<>();
        for (ClientItem ci : clientItems) {
            items.add(ci.getItem());
        }
        model.addAttribute("infoUser", items);

//        if(delivery_type == null){
//            return "userInfoPage";
//        }

        return "infoDeliveryUser";
    }

    @PostMapping(value = "/getItems")
    @ResponseBody
    public String getItems(@RequestParam String name, Model model) {
        Client client = clientRepository.getClientByClient(name);
        System.out.println(client.getClient());
        List<ClientItem> clientItems = (List<ClientItem>) clientItemRepository.getClientItemsByClient(client);
        String str = "{\"data\" : [ ";
//        for (ClientItem clientItem: clientItems) {
//            str+= "\"" + clientItem.getItem().getId() + "\",";
//        }
        for (int i = 0; i < clientItems.size() - 1; i++) {
            str += "\"" + clientItems.get(i).getItem().getId() + "\",";
        }
        str += "\"" + clientItems.get(clientItems.size() - 1).getItem().getId() + "\"";
        str += "]}";
        return str;
    }


    @RequestMapping(value = "/infoClientItem", method = RequestMethod.GET)
    public String modifyItem(Model model) {
        model.addAttribute("title", "Edit");
//        Client client = clientRepository.getClientByClient(name);
//        List<ClientItem> clientItems = (List<ClientItem>) clientItemRepository.getClientItemsByClient(client);
//        model.addAttribute("clientItemRepository", clientItems);


        List<Client> clients = (List<Client>) clientRepository.findAll();
        model.addAttribute("clientRepository", clients);

        List<Item> items = (List<Item>) itemRepository.findAll();
        model.addAttribute("itemRepository", items);

        return "infoClientItem";
    }

    @RequestMapping(value = "/infoClient", method = RequestMethod.GET)
    public String iClient(Model model) {
        List<Client> clients = (List<Client>) clientRepository.findAll(Sort.by(Sort.Direction.ASC, "client"));
        model.addAttribute("clientRepository", clients);
        return "infoClient";
    }

    @PostMapping("/deleteItem")
    public String delItem(@RequestParam String id, Model model) {
//          Client client = clientRepository.getClientByClient(name);
          Item item = itemRepository.findById(new Long(id)).get();
//           ClientItem clientItem = clientItemRepository.()
        clientItemRepository.deleteById(clientItemRepository.getClientItemByItem(item).getId());

        return "deleteItem";
    }

    @PostMapping("/addItem")
    public String addnItem(Model model, @RequestParam String client,
                           @RequestParam String description, @RequestParam String dimensions,
                           @RequestParam String type_packaging, @RequestParam String delivery_type,
                           @RequestParam String date_receipt) {

        Client clients = clientRepository.findById(new Long(client)).get();
        Item item = new Item(description, dimensions, type_packaging, delivery_type, date_receipt);
        itemRepository.save(item);
        ClientItem clientItem = new ClientItem(item, clients);
        clientItemRepository.save(clientItem);
        return "addItem";
    }

    @PostMapping("/editItem")
    public String edItem(Model model, @RequestParam String client, @RequestParam String itemId,
                         @RequestParam String description, @RequestParam String dimensions,
                         @RequestParam String type_packaging, @RequestParam String delivery_type,
                         @RequestParam String date_receipt) {
        Client clientt = clientRepository.findById(new Long(client)).get();
//        Item item = new Item(description, dimensions, type_packaging, delivery_type, date_receipt);
        Item items = itemRepository.findById(new Long(itemId)).get();
//        itemRepository.save(item);
        ClientItem clientItem = clientItemRepository.getClientItemByClientAndItem(clientt, items);
//        clientItem.setItem(item);
        clientItem.getItem().setDescription(description);
        clientItem.getItem().setDimensions(dimensions);
        clientItem.getItem().setType_packaging(type_packaging);
        clientItem.getItem().setDelivery_type(delivery_type);
        clientItem.getItem().setDate_receipt(date_receipt);
        clientItemRepository.save(clientItem);
        return "editItem";
    }

    @RequestMapping(value = "/deleteItem", method = RequestMethod.GET)
    public String deleteItem(Model model, Principal principal) {

        model.addAttribute("title", "Edit");

        List<Client> clients = (List<Client>) clientRepository.findAll();
        model.addAttribute("clientRepository", clients);
//        List <Item> items = (List<Item>) itemRepository.findAll();
//        model.addAttribute("itemRepository", items);
        return "deleteItem";
    }

    @RequestMapping(value = "/addItem", method = RequestMethod.GET)
    public String addItem(Model model, Principal principal) {

        model.addAttribute("title", "Edit");

        List<Client> clients = (List<Client>) clientRepository.findAll();
        model.addAttribute("clientRepository", clients);
//        List <Item> items = (List<Item>) itemRepository.findAll();
//        model.addAttribute("itemRepository", items);
        return "addItem";
    }


    @RequestMapping(value = "/editItem", method = RequestMethod.GET)
    public String editItem(Model model, Principal principal) {

        model.addAttribute("title", "Edit");

        List<Client> clients = (List<Client>) clientRepository.findAll();
        model.addAttribute("clientRepository", clients);
//        List <Item> items = (List<Item>) itemRepository.findAll();
//        model.addAttribute("itemRepository", items);
        return "editItem";
    }

}
