package com.dhhan.demo.controller.openapi;

import com.dhhan.demo.dto.response.CustomResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "컨트롤러를 태그로 관리가능", description="짧은 설명문을 추가")
public interface SampleInterfaceOpenApi {

    @Operation(summary = "여기에다가는 간단한 요약문을 작성한다",
            description = "여기다가 설명문을 적습니다."
    )
    @Schema(
            example = "10",
            description = "number"
    )
    @Parameter(name="X-Custom-Header",description = "헤더설명",schema = @Schema(defaultValue = "exampleValue"))
    public CustomResponse helloWorld(String number);


    public CustomResponse showMemoryInfo();

    public void error();
}
