package pythonService;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.20.0)",
    comments = "Source: EditDist.proto")
public final class EditDistServiceGrpc {

  private EditDistServiceGrpc() {}

  public static final String SERVICE_NAME = "pythonService.EditDistService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<pythonService.EditDist.EditDistRequest,
      pythonService.EditDist.EditDistResponse> getEditDistMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "EditDist",
      requestType = pythonService.EditDist.EditDistRequest.class,
      responseType = pythonService.EditDist.EditDistResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<pythonService.EditDist.EditDistRequest,
      pythonService.EditDist.EditDistResponse> getEditDistMethod() {
    io.grpc.MethodDescriptor<pythonService.EditDist.EditDistRequest, pythonService.EditDist.EditDistResponse> getEditDistMethod;
    if ((getEditDistMethod = EditDistServiceGrpc.getEditDistMethod) == null) {
      synchronized (EditDistServiceGrpc.class) {
        if ((getEditDistMethod = EditDistServiceGrpc.getEditDistMethod) == null) {
          EditDistServiceGrpc.getEditDistMethod = getEditDistMethod = 
              io.grpc.MethodDescriptor.<pythonService.EditDist.EditDistRequest, pythonService.EditDist.EditDistResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "pythonService.EditDistService", "EditDist"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pythonService.EditDist.EditDistRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pythonService.EditDist.EditDistResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new EditDistServiceMethodDescriptorSupplier("EditDist"))
                  .build();
          }
        }
     }
     return getEditDistMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static EditDistServiceStub newStub(io.grpc.Channel channel) {
    return new EditDistServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static EditDistServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new EditDistServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static EditDistServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new EditDistServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class EditDistServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void editDist(pythonService.EditDist.EditDistRequest request,
        io.grpc.stub.StreamObserver<pythonService.EditDist.EditDistResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getEditDistMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getEditDistMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                pythonService.EditDist.EditDistRequest,
                pythonService.EditDist.EditDistResponse>(
                  this, METHODID_EDIT_DIST)))
          .build();
    }
  }

  /**
   */
  public static final class EditDistServiceStub extends io.grpc.stub.AbstractStub<EditDistServiceStub> {
    private EditDistServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private EditDistServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected EditDistServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new EditDistServiceStub(channel, callOptions);
    }

    /**
     */
    public void editDist(pythonService.EditDist.EditDistRequest request,
        io.grpc.stub.StreamObserver<pythonService.EditDist.EditDistResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getEditDistMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class EditDistServiceBlockingStub extends io.grpc.stub.AbstractStub<EditDistServiceBlockingStub> {
    private EditDistServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private EditDistServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected EditDistServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new EditDistServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public pythonService.EditDist.EditDistResponse editDist(pythonService.EditDist.EditDistRequest request) {
      return blockingUnaryCall(
          getChannel(), getEditDistMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class EditDistServiceFutureStub extends io.grpc.stub.AbstractStub<EditDistServiceFutureStub> {
    private EditDistServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private EditDistServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected EditDistServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new EditDistServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<pythonService.EditDist.EditDistResponse> editDist(
        pythonService.EditDist.EditDistRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getEditDistMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_EDIT_DIST = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final EditDistServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(EditDistServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_EDIT_DIST:
          serviceImpl.editDist((pythonService.EditDist.EditDistRequest) request,
              (io.grpc.stub.StreamObserver<pythonService.EditDist.EditDistResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class EditDistServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    EditDistServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return pythonService.EditDist.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("EditDistService");
    }
  }

  private static final class EditDistServiceFileDescriptorSupplier
      extends EditDistServiceBaseDescriptorSupplier {
    EditDistServiceFileDescriptorSupplier() {}
  }

  private static final class EditDistServiceMethodDescriptorSupplier
      extends EditDistServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    EditDistServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (EditDistServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new EditDistServiceFileDescriptorSupplier())
              .addMethod(getEditDistMethod())
              .build();
        }
      }
    }
    return result;
  }
}
