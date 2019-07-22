package com.cyl.it.practice.commconstant;

/**
 * @author chengyuanliang
 * @desc
 * @since 2019-07-21
 */
public class RabbitMQConstant {

    //fanout的模式
    public static final String DEFAULT_FANOUT_EXCHANGE = "default_fanout_exchange";

    public static final String DEFAULT_FANOUT_QUEUE_A = "default_fanout_queue_a";

    public static final String DEFAULT_FANOUT_QUEUE_B = "default_fanout_queue_b";

   // direct 的模式
    public static final String DEFAULT_DIRECT_EXCHANGE = "default_direct_exchange";

    public static final String DEFAULT_DIRECT_QUEUE_A = "default_direct_queue_a";

    public static final String DEFAULT_DIRECT_QUEUE_B = "default_direct_queue_b";

    public static final String DEFAULT_DIRECT_ROUTINGKEY_A = "default_direct_key_a";

    public static final String DEFAULT_DIRECT_ROUTINGKEY_B = "default_direct_key_b";


    // topic 的模式
    public static final String DEFAULT_TOPIC_EXCHANGE = "default_topic_exchange";

    public static final String DEFAULT_TOPIC_QUEUE_A = "default_topic_queue_a";

    public static final String DEFAULT_TOPIC_QUEUE_B = "default_topic_queue_b";

    public static final String DEFAULT_TOPIC_QUEUE_C = "default_topic_queue_c";

    public static final String DEFAULT_TOPIC_ROUTINGKEY_A = "default_topic_key_a.cyl.male.age";

    public static final String DEFAULT_TOPIC_ROUTINGKEY_B = "default_topic_key_b.cyl.male";

    public static final String DEFAULT_TOPIC_ROUTINGKEY_C= "default_topic_key_b.cyl";

    public static final String DEFAULT_TOPIC_ROUTINGKEY_PATTERN_A_= "default_topic_key_b.cyl.*";

    public static final String DEFAULT_TOPIC_ROUTINGKEY_PATTERN_B= "default_topic_key_b.cyl.#";

    public static final String DEFAULT_TOPIC_ROUTINGKEY_PATTERN_C= "default_topic_key_b.*.male";





}
