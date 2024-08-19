package main.dto.rest;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EntitySettingsDto {
    @NotEmpty(message = "Пожалуйста заполните краткое наименование юр. лица," +
            " эксплуатирующей данную систему метрологического обеспечения")
    private String organizationNotation;
    @NotEmpty(message = "Пожалуйста заполните полное наименование юр. лица," +
            " эксплуатирующей даннусю систему метрологического обеспечения")
    private String organizationTitle;
    @NotBlank(message = "Убедитесь в правильности введенного условного шифра знака поверки")
    @Size(min=3, max = 4, message = "Убедитесь в правильности введенного условного шифра знака поверки")
    private String signCipher;
}
