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

package dev.mbo.cucumberspring.util

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.util.MultiValueMap

/**
 * Class for storing http responses so that it can be used in between different calls as needed in Cucumber.
 */
class HttpSession(private val restTemplate: TestRestTemplate) {

    private var latestResponse: ResponseEntity<*>? = null

    fun getLatestResponse(): ResponseEntity<*> {
        return latestResponse!!
    }

    fun executeForString(
        method: HttpMethod,
        url: String,
        body: Any? = null,
        headers: MultiValueMap<String, String> = HttpHeaderBuilder.builder().build()
    ): ResponseEntity<String> {
        execute(
            method = method,
            url = url,
            body = body,
            responseClass = String::class.java,
            headers = headers
        )
        @Suppress("UNCHECKED_CAST") return latestResponse!! as ResponseEntity<String>
    }

    @Suppress("MemberVisibilityCanBePrivate")
    fun <T> execute(
        method: HttpMethod,
        url: String,
        body: Any? = null,
        responseClass: Class<T>,
        headers: MultiValueMap<String, String> = HttpHeaderBuilder.builder().build()
    ): ResponseEntity<T> {
        log.info(
            "{} {} with headers {}",
            method,
            url,
            headers
        )
        latestResponse = restTemplate.exchange(
            url,
            method,
            HttpEntity(
                body,
                headers
            ),
            responseClass
        )
        @Suppress("UNCHECKED_CAST") return latestResponse!! as ResponseEntity<T>
    }

    companion object {
        private val log: Logger = LoggerFactory.getLogger(HttpSession::class.java)
    }

}