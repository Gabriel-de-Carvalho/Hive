package com.tcc.Hive.job;

import com.tcc.Hive.company.Company;
import com.tcc.Hive.user.User;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;

@Document("job")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Job {

    @Id
    private int id;
    @NotNull
    @NotEmpty
    private String jobTitle;
    @NotNull
    @NotEmpty
    private String jobDesc;
    private int numberParticipants;
    private User recrutador;
    @DBRef
    private Company company;
    private float income;
    private ArrayList<User> participants;

    public void joinParticipant(User participant){
        if(participant != null){
            participants.add(participant);
        }
    }
}
