package main.dto.rest;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SettingsDto {
    private String organizationNotation;
    private String organizationTitle;
    private String signCipher;
}
