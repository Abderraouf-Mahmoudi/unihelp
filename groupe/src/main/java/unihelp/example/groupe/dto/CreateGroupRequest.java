package unihelp.example.groupe.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateGroupRequest {
    private String groupName;
    private List<String> userNames;
    private String createdBy;

}
