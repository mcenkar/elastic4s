package com.sksamuel.elastic4s.fields

import com.sksamuel.elastic4s.ext.OptionImplicits._

case class ContextField(name: String, `type`: String, path: Option[String] = None, precision: Option[Int] = None) {

  def path(path: String): ContextField =
    copy(path = path.some)

  def precision(precision: Int): ContextField =
    copy(precision = precision.some)
}
