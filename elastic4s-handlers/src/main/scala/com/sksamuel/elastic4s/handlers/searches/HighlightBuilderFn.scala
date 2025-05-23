package com.sksamuel.elastic4s.handlers.searches

import com.sksamuel.elastic4s.handlers.searches.queries.QueryBuilderFn
import com.sksamuel.elastic4s.json.{XContentBuilder, XContentFactory}
import com.sksamuel.elastic4s.requests.searches.Highlight

object HighlightBuilderFn {

  def apply(highlight: Highlight): XContentBuilder = {

    val builder = XContentFactory.obj()

    highlight.options.boundaryScanner.foreach(builder.field("boundary_scanner", _))
    highlight.options.boundaryScannerLocale.foreach(builder.field("boundary_scanner_locale", _))
    highlight.options.boundaryChars.foreach(chars => builder.field("boundary_chars", String.valueOf(chars)))
    highlight.options.boundaryMaxScan.foreach(builder.field("boundary_max_scan", _))
    highlight.options.fragmenter.foreach(builder.field("fragmenter", _))
    highlight.options.fragmentOffset.foreach(builder.field("fragment_offset", _))
    highlight.options.fragmentSize.foreach(builder.field("fragment_size", _))
    highlight.options.numOfFragments.foreach(builder.field("number_of_fragments", _))
    highlight.options.encoder.foreach(builder.field("encoder", _))
    highlight.options.highlighterType.foreach(builder.field("type", _))
    highlight.options.highlightQuery.map(QueryBuilderFn.apply).foreach { highlight =>
      builder.rawField("highlight_query", highlight)
    }
    if (highlight.options.matchedFields.nonEmpty)
      builder.array("matched_fields", highlight.options.matchedFields.toArray)
    highlight.options.noMatchSize.foreach(builder.field("no_match_size", _))
    highlight.options.order.foreach(builder.field("order", _))
    highlight.options.phraseLimit.foreach(builder.field("phrase_limit", _))
    highlight.options.maxAnalyzedOffset.foreach(builder.field("max_analyzed_offset", _))
    highlight.options.requireFieldMatch.foreach(builder.field("require_field_match", _))
    highlight.options.tagsSchema.foreach(builder.field("tags_schema", _))

    if (highlight.options.postTags.nonEmpty || highlight.options.preTags.nonEmpty) {
      if (highlight.options.postTags.isEmpty) builder.array("post_tags", Array("</em>"))
      else builder.array("post_tags", highlight.options.postTags.toArray)
      if (highlight.options.preTags.isEmpty) builder.array("pre_tags", Array("<em>"))
      else builder.array("pre_tags", highlight.options.preTags.toArray)
    }

    if (highlight.fields.nonEmpty) {
      builder.startObject("fields")
      highlight.fields.foreach { field =>
        builder.rawField(field.field, HighlightFieldBuilderFn(field))
      }
      builder.endObject()
    }

    highlight.options.options.foreach(options => options.foreach { case (k, v) => builder.autofield(k, v) })

    builder.endObject()
    builder
  }
}
