package com.sksamuel.elastic4s.searches.aggs

import com.sksamuel.elastic4s.ScriptBuilder
import com.sksamuel.elastic4s.searches.aggs.pipeline.PipelineAggregationBuilderFn
import org.elasticsearch.search.aggregations.AggregationBuilders
import org.elasticsearch.search.aggregations.metrics.min.MinAggregationBuilder

import scala.collection.JavaConverters._

object MinAggregationBuilder {

  def apply(agg: MinAggregationDefinition): MinAggregationBuilder = {
    val builder = AggregationBuilders.min(agg.name)
    agg.field.foreach(builder.field)
    agg.missing.foreach(builder.missing)
    agg.format.foreach(builder.format)
    agg.script.map(ScriptBuilder.apply).foreach(builder.script)
    agg.subaggs.map(AggregationBuilder.apply).foreach(builder.subAggregation)
    agg.pipelines.map(PipelineAggregationBuilderFn.apply).foreach(builder.subAggregation)
    if (agg.metadata.nonEmpty) builder.setMetaData(agg.metadata.asJava)
    builder
  }
}
