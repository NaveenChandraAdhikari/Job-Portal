package com.jobportal.Job.Portal.dto;

import com.jobportal.Job.Portal.entity.User;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {



        private Long id;


        //defined in validationmessage.prop for deeper msgs
        @NotBlank(message = "{user.name.absent}")
        private String name;

        @NotBlank(message = "{user.email.absent}")
        @Email(message = "{user.email.invalid}")
        private String email;

        @NotBlank(message = "{user.password.absent}")
@Pattern(regexp ="^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,15}$",message = "{user.password.invalid}")
        private String password;


        private AccountType accountType;

        //made this cuz of createProfile as i am not making api for profile ..i just want to create profile when user registers
        private Long profileId;


//        public UserDTO(String id, String email, String password, AccountType accountType) {
//        }


//        convert userdto to entity

        public User toEntity(){

                return new User(this.id,this.name,this.email,this.password,this.accountType,this.profileId);

        }
}
