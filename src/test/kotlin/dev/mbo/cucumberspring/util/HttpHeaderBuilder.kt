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

import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap

class HttpHeaderBuilder {
    private val headers: MultiValueMap<String, String> = LinkedMultiValueMap()
    private var built = false

    /**
     * Simple builder for creating MultiValueMap for http requests. The preferred way is to use constants.
     *
     * Sample:
     *
     * <code>
     *     HttpHeaderBuilder.builder()
     *       .add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
     *       .build()
     * </code>
     *
     * If you want to add a custom header you can also simply add them with strings:
     *
     * <code>
     *     HttpHeaderBuilder.builder()
     *       .add("X-Custom", "123")
     *       .build()
     * </code>
     *
     * @param header The header name.
     * @param value The value to add to this header. Calling add with the same header multiple times appends to existing and does NOT
     *              replace the old value.
     * @return the builder instance so that you can chain the add calls.
     */
    fun add(
        header: String,
        value: String
    ): HttpHeaderBuilder {
        if (header.isEmpty() || value.isEmpty()) {
            throw IllegalArgumentException("header name and value can't be empty")
        }
        if (headers[header] != null) {
            headers[header]!!.add(value)
        } else {
            headers[header] = value
        }
        return this
    }

    /**
     * @return The MultiValueMap based on the methods called before. Can only be run once per instance.
     */
    fun build(): MultiValueMap<String, String> {
        if (built) throw IllegalStateException("build already executed on this builder")
        built = true
        return headers
    }

    companion object {
        fun builder(): HttpHeaderBuilder {
            return HttpHeaderBuilder()
        }
    }

}