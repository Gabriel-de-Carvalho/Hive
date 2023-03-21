package com.tcc.Hive.experience;

import com.tcc.Hive.user.User;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Experience {
    @Id
    private int id;
    private String jobTitle;
    private String jobDesc;
    private String dateInit;
    private String dateEnd;
    private boolean currentJob;
//    private Company company;
}
