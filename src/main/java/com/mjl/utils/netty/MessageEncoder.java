package com.mjl.utils.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * created in 2019/1/8.
 *
 * @author majiali
 * @desc
 */
public class MessageEncoder extends MessageToByteEncoder<Message> {

    //从Message中获取数据，解析成字节后，写入到ByteBuff中
    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) throws Exception {
        Header header = msg.getHeader();
        out.writeByte(MessageDecoder.PACKAGE_TAG);
        out.writeByte(header.getEncode());
        out.writeByte(header.getEncrypt());
        out.writeByte(header.getExtend1());
        out.writeByte(header.getExtend2());
        out.writeBytes(header.getSessionid().getBytes());
        out.writeInt(header.getLength());
        out.writeInt(header.getCammand());
        out.writeBytes(msg.getData().getBytes("UTF-8"));
    }

}
