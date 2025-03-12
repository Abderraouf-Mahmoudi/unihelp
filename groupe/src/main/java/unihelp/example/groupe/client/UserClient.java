package unihelp.example.groupe.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "user", url = "http://localhost:8070" )

public interface UserClient {
    @GetMapping("/users/{userId}/roles") // L'endpoint pour récupérer les rôles d'un utilisateur
    List<String> getUserRoles(@PathVariable("userId") Long userId);
}