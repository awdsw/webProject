package com.example.webProject.controllers;

import com.example.webProject.entity.AppUser;
import com.example.webProject.models.Delivery;
import com.example.webProject.models.Client;
import com.example.webProject.models.ClientItem;
import com.example.webProject.models.Item;
import com.example.webProject.models.ItemDto;
import com.example.webProject.repository.*;
import com.example.webProject.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Controller
public class MainController {

    private static final Comparator<ClientItem> COMPARATOR =
            Comparator.comparing(client -> client.getClient().getClient()); //ссылка на метод --- лямбда выражение

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

    @GetMapping("/manager")
    public String adminPage() {
        return "generalPage";
    }

    @GetMapping("/infoClient")
    public String iClient(Model model) {
        List<Client> clients = new ArrayList<>();
        for (Client client : clientRepository.findAll()) {
            clients.add(client);
        }
        clients.sort(Comparator.comparing(Client::getClient)); //ссылка на метод
        model.addAttribute("clientRepository", clients);
        return "infoClient";
    }

    @GetMapping("/infoClientItem")
    public String modifyItem(Model model) {
        List<ClientItem> clientItems = new ArrayList<>();
        for (ClientItem item : clientItemRepository.findAll()) {
            clientItems.add(item);
        }
        clientItems.sort(COMPARATOR);
        model.addAttribute("items", clientItems);
        return "infoClientItem";
    }

    @GetMapping("/addItem")
    public String addItem(Model model) {
        model.addAttribute("title", "Edit");
        List<Client> clients = (List<Client>) clientRepository.findAll();
        model.addAttribute("clientRepository", clients);
        return "addItem";
    }

    @PostMapping("/addItem")
    public String addnItem(@RequestParam String client,
                           @RequestParam String description, @RequestParam String dimensions,
                           @RequestParam String type_packaging, @RequestParam String delivery_type,
                           @RequestParam String date_receipt) {
        Client clients = clientRepository.findById(Long.valueOf(client)).get();
        Item item = new Item(description, dimensions, type_packaging, delivery_type, date_receipt);
        itemRepository.save(item);
        ClientItem clientItem = new ClientItem(item, clients);
        clientItemRepository.save(clientItem);
        return "addItem";
    }

    @GetMapping("/deleteItem")
    public String deleteItem(Model model) {
        model.addAttribute("title", "Edit");
        List<Client> clients = (List<Client>) clientRepository.findAll();
        model.addAttribute("clientRepository", clients);
        return "deleteItem";
    }

    @PostMapping("/deleteItem")
    public String delItem(@RequestParam String id) {
        Item item = itemRepository.findById(Long.valueOf(id)).get();
        clientItemRepository.deleteById(clientItemRepository.getClientItemByItem(item).getId());
        return "deleteItem";
    }

    @GetMapping("/editItem")
    public String editItem(Model model) {
        model.addAttribute("title", "Edit");
        List<Client> clients = (List<Client>) clientRepository.findAll();
        model.addAttribute("clientRepository", clients);
        return "editItem";
    }

    @PostMapping("/editItem")
    public String edItem(@RequestParam String client, @RequestParam String itemId,
                         @RequestParam String description, @RequestParam String dimensions,
                         @RequestParam String type_packaging, @RequestParam String delivery_type,
                         @RequestParam String date_receipt) {
        Client clientt = clientRepository.findById(Long.valueOf(client)).get();
        Item items = itemRepository.findById(Long.valueOf(itemId)).get();
        ClientItem clientItem = clientItemRepository.getClientItemByClientAndItem(clientt, items);
        clientItem.getItem().setDescription(description);
        clientItem.getItem().setDimensions(dimensions);
        clientItem.getItem().setType_packaging(type_packaging);
        clientItem.getItem().setDelivery_type(delivery_type);
        clientItem.getItem().setDate_receipt(date_receipt);
        clientItemRepository.save(clientItem);
        return "editItem";
    }

    @GetMapping("/employee")
    public String employeePage(Model model) {
        List<ClientItem> items = new ArrayList<>();
        for (Client client : clientRepository.findAll()) {
            for (ClientItem clientItem : clientItemRepository.getClientItemsByClient(client)) {
                if (!clientItem.isOrder_status() && clientItem.getItem().getDelivery_type().equals(Delivery.DELIVERY)) {
                    items.add(clientItem);
                }
            }
        }
        items.sort(COMPARATOR);
        model.addAttribute("itemRepository", items);
        return "employeePage";
    }

    @GetMapping("/infoDE")
    public String infoDilEmp(Model model) {
        List<ClientItem> clientItems = new ArrayList<>();
        for (ClientItem item : clientItemRepository.findAll()) {
            if (item.getItem().getDelivery_type().equals(Delivery.DELIVERY) && item.isOrder_status()) {
                clientItems.add(item);
            }
        }
        clientItems.sort(COMPARATOR);
        model.addAttribute("items", clientItems);
        return "infoDeliveryEmployee";
    }

    @PostMapping("/infoDE")
    public String deInfo(Model model, @RequestParam String id) {
        ClientItem clientItem =
                clientItemRepository.getClientItemByItem(itemRepository.findById(Long.valueOf(id)).get());
        clientItem.setOrder_status(true);
        clientItemRepository.save(clientItem);
        List<ClientItem> clientItems = new ArrayList<>();
        for (ClientItem item : clientItemRepository.findAll()) {
            if (item.getItem().getDelivery_type().equals(Delivery.DELIVERY) && item.isOrder_status()) {
                clientItems.add(item);
            }
        }
        model.addAttribute("items", clientItems);
        return "infoDeliveryEmployee";
    }

    @GetMapping("/userInfo")
    public String userInfo(Model model, Principal principal) {
        User loginedUser = (User) ((Authentication) principal).getPrincipal();
        AppUser appUser = userRepository.getAppUserByUserName(loginedUser.getUsername());
        List<ClientItem> clientItems = (List<ClientItem>) clientItemRepository.getItemsByClient(clientRepository.findById(appUser.getUserId()).get());
        List<Item> items = new ArrayList<>();
        for (ClientItem ci : clientItems) {
            Item item = ci.getItem();
            if (item.getDelivery_type().equals("")) {
                items.add(ci.getItem());
            }
        }
        model.addAttribute("cLIt", clientRepository.findById(appUser.getUserId()).get().getClient());
        model.addAttribute("userInfo", items);
        return "userInfoPage";
    }

    @GetMapping("/infoDU")
    public String getData(Model model, Principal principal) {
        User loginedUser = (User) ((Authentication) principal).getPrincipal();
        AppUser appUser = userRepository.getAppUserByUserName(loginedUser.getUsername());
        List<ClientItem> clientItems = (List<ClientItem>) clientItemRepository
                .getItemsByClient(clientRepository.findById(appUser.getUserId()).get());
        List<ItemDto> items = new ArrayList<>();
        for (ClientItem ci : clientItems) {
            if (!ci.getItem().getDelivery_type().equals("")) {
                items.add(new ItemDto(ci.getItem(), ci.isOrder_status()));
            }
        }
        model.addAttribute("infoUser", items);
        return "infoDeliveryUser";
    }

    @PostMapping("/infoDU")
    public String updateData(Model model, Principal principal,
                             @RequestParam String id, @RequestParam String delivery_type) {
        // Находим нужную запись по ID
        Item item = itemRepository.findById(Long.valueOf(id)).get();
        item.setDelivery_type(delivery_type);
        itemRepository.save(item);
        User loginedUser = (User) ((Authentication) principal).getPrincipal();
        AppUser appUser = userRepository.getAppUserByUserName(loginedUser.getUsername());
        List<ClientItem> clientItems = (List<ClientItem>) clientItemRepository
                .getItemsByClient(clientRepository.findById(appUser.getUserId()).get());
        List<ItemDto> items = new ArrayList<>();
        for (ClientItem ci : clientItems) {
            if (!ci.getItem().getDelivery_type().equals("")) {
                items.add(new ItemDto(ci.getItem(), ci.isOrder_status()));
            }
        }
        model.addAttribute("infoUser", items);
        return "infoDeliveryUser";
    }

    @GetMapping({"/", "/welcome"})
    public String welcomePage(Model model) {
        model.addAttribute("title", "Welcome");
        model.addAttribute("message", "This is welcome page!");
        return "welcomePage";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "loginPage";
    }

    @GetMapping("/logoutSuccessful")
    public String logoutSuccessfulPage(Model model) {
        model.addAttribute("title", "Logout");
        return "logoutSuccessfulPage";
    }

    @GetMapping("/403")
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

    @PostMapping(value = "/getItems")
    @ResponseBody
    public String getItems(@RequestParam String name) {
        Client client = clientRepository.getClientByClient(name);
        System.out.println(client.getClient());
        List<ClientItem> clientItems = (List<ClientItem>) clientItemRepository.getClientItemsByClient(client);
        String str = "{\"data\" : [ ";

        for (int i = 0; i < clientItems.size() - 1; i++) {
            str += "\"" + clientItems.get(i).getItem().getId() + "\",";
        }
        str += "\"" + clientItems.get(clientItems.size() - 1).getItem().getId() + "\"";
        str += "]}";
        return str;
    }
}