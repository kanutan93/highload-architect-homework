box.cfg{}

dialog_space = box.schema.space.create('dialog', {if_not_exists = true})
dialog_space:format({
    {name = 'id', type = 'unsigned'},
    {name = 'user1_id', type = 'unsigned'},
    {name = 'user2_id', type = 'unsigned'}
})
dialog_space:create_index('dialog_idx', {type = 'tree', parts = {'id'}}, {if_not_exists = true})
dialog_space:create_index('dialog_comp_idx', {type = 'tree', parts = {'user1_id', 'user2_id'}}, {if_not_exists = true})
dialog_id_seq = box.schema.sequence.create('dialog_id_sequence')

message_space = box.schema.space.create('message', {if_not_exists = true})
message_space:format({
    {name = 'id', type = 'unsigned'},
    {name = 'from_id', type = 'unsigned'},
    {name = 'to_id', type = 'unsigned'},
    {name = 'text', type = 'string'},
    {name = 'dialog_id', type = 'unsigned'},
})
message_space:create_index('message_idx', {type = 'tree', parts = {'id', 'dialog_id'}}, {if_not_exists = true})
message_space:create_index('message_comp_idx', {type = 'tree', parts = {'dialog_id'}}, {if_not_exists = true})
message_id_seq = box.schema.sequence.create('message_id_sequence', {if_not_exists = true})

os_date = require('os').date
os_time = require('os').time

function get_dialog_id(currentUserId, userId)
    return dialog_space.index.dialog_comp_idx:select {currentUserId, userId}
end

function create_dialog(currentUserId, userId)
    dialog_space:insert{dialog_id_seq:next(), currentUserId, userId}
end

function get_messages(dialog_id)
    return message_space.index.message_comp_idx:select {dialogId}
end

function create_message(fromId, toId, text, dialogId)
    message_space:insert{message_id_seq:next(), fromId, toId, text, dialogId}
end