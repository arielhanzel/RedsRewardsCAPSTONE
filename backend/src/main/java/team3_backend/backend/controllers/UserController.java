package team3_backend.backend.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import team3_backend.backend.models.ApplicationUser;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {

    @GetMapping("/")
    public String helloUserController(){
        return "Hi user, you cannot alter database, sorry for that!";
    }

    private ProductService productService;
    

    public ApplicationUser getProductByuserId(@PathVariable Integer userId) {
        ApplicationUser product = productService.getProductById(userId);
        // Convert the entity to a DTO
        ApplicationUser productDTO = new ApplicationUser(ApplicationUser.getUserId(), ApplicationUser.getpoints(), ApplicationUser.getregisteredClasses(), ApplicationUser.getcheckinlist(), ApplicationUser.getcheckoutlist(), ApplicationUser.getclassesAttended(), ApplicationUser.getrewardRedemption());
        return productDTO;
    }
}

