/*
 * Copyright (c) 2023.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.mbo.cucumberspring.features

import io.cucumber.java.en.And
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import org.assertj.core.api.Assertions.assertThat
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import dev.mbo.cucumberspring.util.HttpHeaderBuilder
import dev.mbo.cucumberspring.util.HttpSession

class VersionFeature @Autowired constructor(
    restTemplate: TestRestTemplate,
) : CucumberContext() {

    @LocalServerPort
    private var port = 0
    private val httpSession = HttpSession(restTemplate)

    @When("^the client calls /version$")
    fun the_client_issues_GET_version() {
        httpSession.executeForString(
            url = "http://localhost:$port/version",
            method = HttpMethod.GET,
            headers = HttpHeaderBuilder.builder().add(
                HttpHeaders.ACCEPT,
                MediaType.APPLICATION_JSON_VALUE
            ).build()
        )
    }

    @Then("^the client receives status code of (\\d+)$")
    fun the_client_receives_status_code_of(statusCode: Int) {
        assertThat(httpSession.getLatestResponse().statusCode.value()).isEqualTo(statusCode)
    }

    @And("^the client receives server version (.+)$")
    fun the_client_receives_server_version_body(version: String) {
        assertThat(httpSession.getLatestResponse().body as String).isEqualTo(version)
    }

}