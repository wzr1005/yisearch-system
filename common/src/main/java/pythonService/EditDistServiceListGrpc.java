package pythonService;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.20.0)",
    comments = "Source: EditDistList.proto")
public final class EditDistServiceListGrpc {

  private EditDistServiceListGrpc() {}

  public static final String SERVICE_NAME = "pythonService.EditDistServiceList";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<pythonService.EditDistList.EditDistRequest,
      pythonService.EditDistList.EditDistResponse> getEditDistMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "EditDist",
      requestType = pythonService.EditDistList.EditDistRequest.class,
      responseType = pythonService.EditDistList.EditDistResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<pythonService.EditDistList.EditDistRequest,
      pythonService.EditDistList.EditDistResponse> getEditDistMethod() {
    io.grpc.MethodDescriptor<pythonService.EditDistList.EditDistRequest, pythonService.EditDistList.EditDistResponse> getEditDistMethod;
    if ((getEditDistMethod = EditDistServiceListGrpc.getEditDistMethod) == null) {
      synchronized (EditDistServiceListGrpc.class) {
        if ((getEditDistMethod = EditDistServiceListGrpc.getEditDistMethod) == null) {
          EditDistServiceListGrpc.getEditDistMethod = getEditDistMethod = 
              io.grpc.MethodDescriptor.<pythonService.EditDistList.EditDistRequest, pythonService.EditDistList.EditDistResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "pythonService.EditDistServiceList", "EditDist"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pythonService.EditDistList.EditDistRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pythonService.EditDistList.EditDistResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new EditDistServiceListMethodDescriptorSupplier("EditDist"))
                  .build();
          }
        }
     }
     return getEditDistMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static EditDistServiceListStub newStub(io.grpc.Channel channel) {
    return new EditDistServiceListStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static EditDistServiceListBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new EditDistServiceListBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static EditDistServiceListFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new EditDistServiceListFutureStub(channel);
  }

  /**
   */
  public static abstract class EditDistServiceListImplBase implements io.grpc.BindableService {

    /**
     */
    public void editDist(pythonService.EditDistList.EditDistRequest request,
        io.grpc.stub.StreamObserver<pythonService.EditDistList.EditDistResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getEditDistMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getEditDistMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                pythonService.EditDistList.EditDistRequest,
                pythonService.EditDistList.EditDistResponse>(
                  this, METHODID_EDIT_DIST)))
          .build();
    }
  }

  /**
   */
  public static final class EditDistServiceListStub extends io.grpc.stub.AbstractStub<EditDistServiceListStub> {
    private EditDistServiceListStub(io.grpc.Channel channel) {
      super(channel);
    }

    private EditDistServiceListStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected EditDistServiceListStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new EditDistServiceListStub(channel, callOptions);
    }

    /**
     */
    public void editDist(pythonService.EditDistList.EditDistRequest request,
        io.grpc.stub.StreamObserver<pythonService.EditDistList.EditDistResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getEditDistMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class EditDistServiceListBlockingStub extends io.grpc.stub.AbstractStub<EditDistServiceListBlockingStub> {
    private EditDistServiceListBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private EditDistServiceListBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected EditDistServiceListBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new EditDistServiceListBlockingStub(channel, callOptions);
    }

    /**
     */
    public pythonService.EditDistList.EditDistResponse editDist(pythonService.EditDistList.EditDistRequest request) {
      return blockingUnaryCall(
          getChannel(), getEditDistMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class EditDistServiceListFutureStub extends io.grpc.stub.AbstractStub<EditDistServiceListFutureStub> {
    private EditDistServiceListFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private EditDistServiceListFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected EditDistServiceListFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new EditDistServiceListFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<pythonService.EditDistList.EditDistResponse> editDist(
        pythonService.EditDistList.EditDistRequest request) {
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
    private final EditDistServiceListImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(EditDistServiceListImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_EDIT_DIST:
          serviceImpl.editDist((pythonService.EditDistList.EditDistRequest) request,
              (io.grpc.stub.StreamObserver<pythonService.EditDistList.EditDistResponse>) responseObserver);
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

  private static abstract class EditDistServiceListBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    EditDistServiceListBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return pythonService.EditDistList.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("EditDistServiceList");
    }
  }

  private static final class EditDistServiceListFileDescriptorSupplier
      extends EditDistServiceListBaseDescriptorSupplier {
    EditDistServiceListFileDescriptorSupplier() {}
  }

  private static final class EditDistServiceListMethodDescriptorSupplier
      extends EditDistServiceListBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    EditDistServiceListMethodDescriptorSupplier(String methodName) {
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
      synchronized (EditDistServiceListGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new EditDistServiceListFileDescriptorSupplier())
              .addMethod(getEditDistMethod())
              .build();
        }
      }
    }
    return result;
  }
}
