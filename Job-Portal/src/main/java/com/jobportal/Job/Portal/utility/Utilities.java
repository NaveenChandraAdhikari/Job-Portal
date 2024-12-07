package com.jobportal.Job.Portal.utility;

import com.jobportal.Job.Portal.entity.Sequence;
import com.jobportal.Job.Portal.exception.JobPortalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;


@Component
public class Utilities {

    //in static autowired not works so we inject by setter injecion instead of field

    private static MongoOperations mongoOperations;

    //this is setter injection
    @Autowired
    public void setMongoOperations(MongoOperations mongoOperation){
        Utilities.mongoOperations=mongoOperation;
    }

    public static Long getNextSequence(String key) throws JobPortalException {

        Query query=new Query(Criteria.where("_id").is(key));
        Update update=new Update();
        update.inc("seq",1);
        FindAndModifyOptions options = new FindAndModifyOptions();
        options.returnNew(true);


        Sequence seq = mongoOperations.findAndModify(query,update,options,Sequence.class);
        if(seq==null)throw new JobPortalException("Unable to get sequence id for key: "+key);





        return seq.getSeq();
    }

//    to generate otp
    public static String generateOTP(){

        StringBuilder otp = new StringBuilder();
        SecureRandom random = new SecureRandom();


        for (int i = 0; i <6 ; i++) {

            otp.append(random.nextInt(10));

        }
        return otp.toString();
    }

}
