package com.ssafy.andback.core.domain.auth;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class GoogleUser {
    private String id;
    private String email;
    private Boolean verifiedEmail;
    private String name;
    private String givenName;
    private String familyName;
    private String picture;
    private String locale;
}