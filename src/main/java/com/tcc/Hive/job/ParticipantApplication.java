package com.tcc.Hive.job;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParticipantApplication {

    @Id
    private String id;

    private String idApplicant;

    private Binary pdfUser;
}
