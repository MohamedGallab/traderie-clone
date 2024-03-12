CREATE KEYSPACE messaging WITH replication = { 'class': 'SimpleStrategy',
'replication_factor': 3 };

CREATE TABLE messaging.messages (
    message_id uuid,
    sender_id uuid,
    receiver_id uuid,
    message_text text,
    created_at timestamp,
    chat_id uuid,
    PRIMARY KEY (chat_id, created_at, message_id)
);

CREATE TABLE messaging.chats (
    chat_id uuid PRIMARY KEY,
    initiator_id uuid,
    receiver_id uuid,
    created_at timestamp,
    chat_id uuid,
    is_accepted boolean,
    is_archived boolean,
);