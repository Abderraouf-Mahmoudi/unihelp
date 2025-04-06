package unihelp.example.groupe.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import unihelp.example.groupe.dto.UserDTO;
import java.util.List;
@FeignClient(name = "user", url = "http://localhost:8070")
public interface UserClient {
    @GetMapping("/api/users/{id}")
    UserDTO getUserById(@PathVariable("id") Long id);
    @GetMapping("/api/users")
    List<UserDTO> getAllUsers();
    @GetMapping("/api/users/by-username/{username}")
    UserDTO getUserByUsername(@PathVariable("username") String username);
}
