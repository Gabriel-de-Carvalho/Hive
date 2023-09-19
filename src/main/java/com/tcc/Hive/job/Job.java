package com.tcc.Hive.job;

import com.tcc.Hive.user.UserHive;
import lombok.*;
import org.springframework.data.annotation.Id;
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
    private String id;
    @NotNull
    @NotEmpty
    private String jobTitle;
    @NotNull
    @NotEmpty
    private String jobDesc;
    @NotNull
    @NotEmpty
        private String companyId;
    private float income;
    @NotNull
    @NotEmpty
    private boolean negotiable;
    private String typeOfJob;
    private String modality;
    private ArrayList<ParticipantApplication> participants;
    private String seniority;
    private boolean active;

    public void joinParticipant(ParticipantApplication application){
        if(application != null){
            if(participants == null){
                setParticipants(new ArrayList<>());
            }
            participants.add(application);
        }
    }
}
