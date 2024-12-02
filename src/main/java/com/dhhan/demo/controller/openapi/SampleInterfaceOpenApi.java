package com.dhhan.demo.controller.openapi;

import com.dhhan.demo.dto.LoginDTO;
import com.dhhan.demo.dto.SampleDTO;
import com.dhhan.demo.dto.response.CustomResponse;
import com.dhhan.demo.dto.type.CustomStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

@Tag(name = "컨트롤러를 태그로 관리가능", description="짧은 설명문을 추가")
public interface SampleInterfaceOpenApi {

    @Operation(summary = "여기에다가는 간단한 요약문을 작성한다",
            description = "여기다가 설명문을 적습니다.",
            parameters = {
                    @Parameter(name = "X-Custom-Header", description = "헤더 설명", in = ParameterIn.HEADER, required = true, schema = @Schema(defaultValue = "exampleValue")),
                    @Parameter(name = "number", description = "parameter", required = true, schema = @Schema(defaultValue = "1000"))
            }
    )
    public CustomResponse helloWorld(LoginDTO loginDTO, String number);


    @RequestBody(
            description = "요청 바디입니다. 기본값은 'defaultValue'입니다.",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                            name = "Default Request",
                            value = "{ \"test\": \"defaultValue\", \"testNum\": 0 }"
                    )
            )
    )
    public CustomResponse helloWorld2(SampleDTO sampleDTO);

    public CustomResponse showMemoryInfo();

    public void error();
}
