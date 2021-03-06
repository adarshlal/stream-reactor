/**
  * Copyright 2016 Datamountaineer.
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
  **/

package com.datamountaineeer.streamreactor.connect.blockchain.data

import org.apache.kafka.connect.data.{Schema, SchemaBuilder, Struct}

case class Input(sequence: Long, prev_out: Option[Output], script: String)

object Input {
  val ConnectSchema = SchemaBuilder.struct
    .name("datamountaineer.blockchain.input")
    .doc("The input instance part of a transaction.")
    .field("sequence", Schema.INT64_SCHEMA)
    .field("prev_out", Output.ConnectSchema)
    .field("script", Schema.STRING_SCHEMA)
    .build()

  implicit class InputToStructConverter(val input: Input) extends AnyVal {
    def toStruct() = {
      val struct = new Struct(ConnectSchema)
        .put("sequence", input.sequence)
        .put("script", input.script)

      input.prev_out.foreach(po=>struct.put("prev_out", po.toStruct()))
      struct
    }
  }

}