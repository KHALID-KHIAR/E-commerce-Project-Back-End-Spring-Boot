package com.kh.EcomercebackEndspringboot.Entity.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GlobalResponse<T> {
    private String Status_code ;
//    private String token ;
    private T response ;

//    public GlobalResponse(String status_code,String token){
//        this.Status_code=status_code;
//        this.token=token;
//    }
//    public GlobalResponse(String status_code,T response){
//        this.Status_code=status_code;
//        this.response=response;
//    }
}
