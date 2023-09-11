package com.tcc.Hive.experience;

import lombok.*;
import org.springframework.data.annotation.Id;

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
    private String companyId;
    private boolean currentJob;
//    private Company company;
}
