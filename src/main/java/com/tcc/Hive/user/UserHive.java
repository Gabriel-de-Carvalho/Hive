package com.tcc.Hive.user;


import com.tcc.Hive.experience.Experience;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Document("user")
@Getter
@Setter
public class UserHive {
    @NotNull
    @NotEmpty
    private String user;
    @NotNull
    @NotEmpty
    @Id
    private String email;
    @NotNull
    @NotEmpty
    private String password;
    private String currentJob;
    private String bio;
    private ArrayList<String> habilities;
    private ArrayList<String> ownedCompaniesIds;
    private ArrayList<Experience> experiences;
    private ArrayList<GrantedAuthority> roles;
    private ArrayList<String> jobOpportunitiesIds;
}
