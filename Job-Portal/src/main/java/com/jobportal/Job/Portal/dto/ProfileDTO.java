package com.jobportal.Job.Portal.dto;

import com.jobportal.Job.Portal.entity.Profile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDTO {


        @Id
        private Long id;

        private String email;

        private String jobTitle;

        private String company;

        private String location;

        private String about;

        private List<String> skills;

        private List<Experience> experiences;

        private List<Certification>certifications;


    public Profile toEntity(){
        return new Profile(this.id,this.email,this.jobTitle,this.company,this.location,this.about,this.skills,this.experiences,this.certifications);

    }

    }
