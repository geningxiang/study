package com.genx.mystudy.engine;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2018/12/18 13:45
 */
public interface ISocketMsgListener {

    void onMessage(StringBuffer sb, String data);

}
