/*
 * Copyright 2014-2017 Real Logic Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.aeron;

/**
 * Interface for notification of {@link Counter}s being removed via an {@link Aeron} client.
 */
@FunctionalInterface
public interface UnavailableCounterHandler
{
    /**
     * Method called by Aeron to deliver notification of counter being removed.
     *
     * @param registrationId for the counter
     * @param counterId      that is unavailable
     */
    void onUnavailableCounter(long registrationId, int counterId);
}