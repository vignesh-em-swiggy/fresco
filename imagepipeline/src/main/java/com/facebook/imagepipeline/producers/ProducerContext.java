/*
 * Copyright (c) Meta Platforms, Inc. and affiliates.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.facebook.imagepipeline.producers;

import com.facebook.fresco.middleware.HasExtraData;
import com.facebook.imagepipeline.common.Priority;
import com.facebook.imagepipeline.core.ImagePipelineConfigInterface;
import com.facebook.imagepipeline.image.EncodedImageOrigin;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.infer.annotation.Nullsafe;
import javax.annotation.Nullable;

/**
 * Used to pass context information to producers.
 *
 * <p>Object implementing this interface is passed to all producers participating in pipeline
 * request {@see Producer#produceResults}. Its responsibility is to instruct producers which image
 * should be fetched/decoded/resized/cached etc. This class also handles request cancellation.
 *
 * <p>In order to be notified when cancellation is requested, a producer should use the {@code
 * runOnCancellationRequested} method which takes an instance of Runnable and executes it when the
 * pipeline client cancels the image request.
 */
@Nullsafe(Nullsafe.Mode.STRICT)
public interface ProducerContext extends HasExtraData {

  /** @return image request that is being executed */
  ImageRequest getImageRequest();

  /** @return id of this request */
  String getId();

  /** @return optional id of the UI component requesting the image */
  @Nullable
  String getUiComponentId();

  /** @return ProducerListener2 for producer's events */
  ProducerListener2 getProducerListener();

  /** @return the {@link Object} that indicates the caller's context */
  Object getCallerContext();

  /** @return the lowest permitted {@link ImageRequest.RequestLevel} */
  ImageRequest.RequestLevel getLowestPermittedRequestLevel();

  /** @return true if the request is a prefetch, false otherwise. */
  boolean isPrefetch();

  /** @return priority of the request. */
  Priority getPriority();

  /** @return true if request's owner expects intermediate results */
  boolean isIntermediateResultExpected();

  /**
   * Adds callbacks to the set of callbacks that are executed at various points during the
   * processing of a request.
   *
   * @param callbacks callbacks to be executed
   */
  void addCallbacks(ProducerContextCallbacks callbacks);

  ImagePipelineConfigInterface getImagePipelineConfig();

  EncodedImageOrigin getEncodedImageOrigin();

  void setEncodedImageOrigin(EncodedImageOrigin encodedImageOrigin);

  /**
   * Helper to set {@link HasExtraData#KEY_ORIGIN} and {@link HasExtraData#KEY_ORIGIN_SUBCATEGORY}
   */
  void putOriginExtra(@Nullable String origin, @Nullable String subcategory);

  /** Helper to set {@link HasExtraData#KEY_ORIGIN} */
  void putOriginExtra(@Nullable String origin);
}
