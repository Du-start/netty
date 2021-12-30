package com.du.netty.inboundhandlerandoutboundhandler;


import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

public class MyReplayingDecoder extends ReplayingDecoder<Void> {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("MyByteToLongDecoder2 被调用");
        //在 ReplayingDecoder扩展了ByteToMessageDecoder类，使用这个类不需要调用 readableBytes() 方法
        // 不需要判断数据是否足够读取，内部会进行处理判断
        out.add(in.readLong());
    }
}
