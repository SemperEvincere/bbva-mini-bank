package com.bbva.minibank.presentation.request;

import lombok.Getter;

@Getter
public class ClientCreateRequest {

  private String lastName;
  private String firstName;
  private String email;
  private String phone;
  private String address;

}
