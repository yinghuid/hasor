package net.hasor.rsf.protocol.codec;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import java.io.IOException;
import net.hasor.rsf.protocol.socket.RequestSocketMessage;
/**
 * Protocol Interface,for custom network protocol
 * @version : 2014年10月25日
 * @author 赵永春(zyc@hasor.net)
 */
public class RpcRequestProtocol implements Protocol<RequestSocketMessage> {
    /**encode Message to byte & write to network framework*/
    public void encode(RequestSocketMessage reqMsg, ByteBuf buf) throws IOException {
        //* --------------------------------------------------------bytes =13
        //* byte[1]  version                              RSF版本(0xC1)
        buf.writeByte(reqMsg.getVersion());
        //* byte[8]  requestID                            请求ID
        buf.writeLong(reqMsg.getRequestID());
        ByteBuf requestBody = this.encodeRequest(reqMsg);
        //* byte[4]  contentLength                        内容大小(max = 16MB)
        buf.writeInt(requestBody.readableBytes());
        //
        buf.writeBytes(requestBody);
    }
    //
    private ByteBuf encodeRequest(RequestSocketMessage reqMsg) {
        ByteBuf bodyBuf = ByteBufAllocator.DEFAULT.heapBuffer();
        //* --------------------------------------------------------bytes =10
        //* byte[2]  servicesName-(attr-index)            远程服务名
        bodyBuf.writeShort(reqMsg.getServiceName());
        //* byte[2]  servicesGroup-(attr-index)           远程服务分组
        bodyBuf.writeShort(reqMsg.getServiceGroup());
        //* byte[2]  servicesVersion-(attr-index)         远程服务版本
        bodyBuf.writeShort(reqMsg.getServiceVersion());
        //* byte[2]  servicesMethod-(attr-index)          远程服务方法名
        bodyBuf.writeShort(reqMsg.getTargetMethod());
        //* byte[2]  serializeType-(attr-index)           序列化策略
        bodyBuf.writeShort(reqMsg.getSerializeType());
        //* --------------------------------------------------------bytes =1 ~ 1021
        //* byte[1]  paramCount                           参数总数
        int[] paramMapping = reqMsg.getParameters();
        bodyBuf.writeByte(paramMapping.length);
        for (int i = 0; i < paramMapping.length; i++) {
            //* byte[4]  ptype-0-(attr-index,attr-index)  参数类型1
            //* byte[4]  ptype-1-(attr-index,attr-index)  参数类型2
            bodyBuf.writeInt(paramMapping[i]);
        }
        //* --------------------------------------------------------bytes =1 ~ 1021
        //* byte[1]  optionCount                          选项参数总数
        int[] optionMapping = reqMsg.getOptions();
        bodyBuf.writeByte(optionMapping.length);
        for (int i = 0; i < optionMapping.length; i++) {
            //* byte[4]  ptype-0-(attr-index,attr-index)  选项参数1
            //* byte[4]  ptype-1-(attr-index,attr-index)  选项参数2
            bodyBuf.writeInt(optionMapping[i]);
        }
        //* --------------------------------------------------------bytes =6 ~ 8192
        //* byte[2]  attrPool-size (Max = 2047)           池大小 0x07FF
        int[] poolData = reqMsg.getPoolData();
        bodyBuf.writeShort(poolData.length);
        for (int i = 0; i < poolData.length; i++) {
            //* byte[4]  ptype-0-(attr-index,attr-index)  属性1大小
            //* byte[4]  ptype-1-(attr-index,attr-index)  属性1大小
            bodyBuf.writeInt(poolData[i]);
        }
        //* --------------------------------------------------------bytes =n
        //* dataBody                                      数据内容
        reqMsg.fillTo(bodyBuf);
        return bodyBuf;
    }
    //
    //
    //
    /**decode stream to object*/
    public RequestSocketMessage decode(ByteBuf buf) throws IOException {
        //* --------------------------------------------------------bytes =13
        //* byte[1]  version                              RSF版本(0xC1)
        byte version = buf.readByte();
        //* byte[8]  requestID                            包含的请求ID
        long requestID = buf.readLong();
        //* byte[4]  contentLength                        内容大小
        buf.skipBytes(4);
        //
        RequestSocketMessage req = new RequestSocketMessage();
        req.setVersion(version);
        req.setRequestID(requestID);
        //* --------------------------------------------------------bytes =10
        //* byte[2]  servicesName-(attr-index)            远程服务名
        req.setServiceName(buf.readShort());
        //* byte[2]  servicesGroup-(attr-index)           远程服务分组
        req.setServiceGroup(buf.readShort());
        //* byte[2]  servicesVersion-(attr-index)         远程服务版本
        req.setServiceVersion(buf.readShort());
        //* byte[2]  servicesMethod-(attr-index)          远程服务方法名
        req.setTargetMethod(buf.readShort());
        //* byte[2]  serializeType-(attr-index)           序列化策略
        req.setSerializeType(buf.readShort());
        //* --------------------------------------------------------bytes =1 ~ 1021
        //* byte[1]  paramCount                           参数总数
        byte paramCount = buf.readByte();
        for (int i = 0; i < paramCount; i++) {
            //* byte[4]  ptype-0-(attr-index,attr-index)  参数类型
            int mergeData = buf.readInt();
            req.addParameter(mergeData);
        }
        //* --------------------------------------------------------bytes =1 ~ 1021
        //* byte[1]  optionCount                          选项参数总数
        byte optionCount = buf.readByte();
        for (int i = 0; i < optionCount; i++) {
            //* byte[4]  attr-0-(attr-index,attr-index)   选项参数
            int mergeData = buf.readInt();
            req.addOption(mergeData);
        }
        //* --------------------------------------------------------bytes =6 ~ 8192
        //* byte[2]  attrPool-size (Max = 2047)           池大小
        short attrPoolSize = buf.readShort();
        for (int i = 0; i < attrPoolSize; i++) {
            //* byte[4] att-length                        属性1大小
            int length = buf.readInt();
            req.addPoolData(length);
        }
        //* --------------------------------------------------------bytes =n
        //* dataBody                                      数据内容
        req.fillFrom(buf.readBytes(req.getPoolSize()));
        return req;
    }
}