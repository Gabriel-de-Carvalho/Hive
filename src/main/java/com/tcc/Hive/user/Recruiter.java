package com.tcc.Hive.user;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Document("user")
public class Recruiter extends UserHive {

}
