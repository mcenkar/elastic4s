package com.sksamuel.elastic4s.searches.aggs

import com.sksamuel.elastic4s.ScriptBuilder
import com.sksamuel.elastic4s.searches.aggs.pipeline.PipelineAggregationBuilderFn
import org.elasticsearch.search.aggregations.AggregationBuilders
import org.elasticsearch.search.aggregations.metrics.avg.AvgAggregationBuilder

import scala.collection.JavaConverters._

object AvgAggregationBuilder {
  def apply(agg: AvgAggregationDefinition): AvgAggregationBuilder = {
    val builder = AggregationBuilders.avg(agg.name)
    agg.field.foreach(builder.field)
    agg.subaggs.map(AggregationBuilder.apply).foreach(builder.subAggregation)
    agg.pipelines.map(PipelineAggregationBuilderFn.apply).foreach(builder.subAggregation)
    agg.script.map(ScriptBuilder.apply).foreach(builder.script)
    if (agg.metadata.nonEmpty) builder.setMetaData(agg.metadata.asJava)
    builder
  }
}
