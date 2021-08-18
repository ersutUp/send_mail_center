package top.ersut.mail.send.model.v1.mail.param;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SendMailParam {
    @NotEmpty
    private String title;

    @NotEmpty
    private String content;

    @NotNull
    @Min(1)
    private Long signId;

}
