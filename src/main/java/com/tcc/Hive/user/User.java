package com.tcc.Hive.user;


import com.tcc.Hive.experience.Experience;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Document("user")
@Getter
@Setter
public class User {


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
    private String cargo;
    private String bio;
    private ArrayList<String> habilidades;
    private ArrayList<Experience> experiences;
}
