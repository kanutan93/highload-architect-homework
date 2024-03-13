box.cfg{}

box.schema.space.create('dialog', {if_not_exists = true})
box.space.dialog:format({
    {name = 'id', type = 'unsigned'},
    {name = 'user1_id', type = 'unsigned'},
    {name = 'user2_id', type = 'unsigned'}
})
box.space.dialog:create_index('dialog_idx', {type = 'tree', parts = {'id'}, if_not_exists = true})
box.space.dialog:create_index('dialog_comp_idx', {type = 'tree', parts = {'user1_id', 'user2_id'}, if_not_exists = true})
box.schema.sequence.create('dialog_id_sequence', {if_not_exists = true})

box.schema.space.create('message', {if_not_exists = true})
box.space.message:format({
    {name = 'id', type = 'unsigned'},
    {name = 'from_id', type = 'unsigned'},
    {name = 'to_id', type = 'unsigned'},
    {name = 'text', type = 'string'},
    {name = 'dialog_id', type = 'unsigned'},
})
box.space.message:create_index('message_idx', {type = 'tree', parts = {'id'}, if_not_exists = true})
box.space.message:create_index('message_comp_idx', {type = 'tree', parts = {'dialog_id'}, if_not_exists = true, unique = false})
box.space.message:create_index('message_from_to_comp_idx', {type = 'tree', parts = {'from_id', 'to_id'}, if_not_exists = true, unique = false})
box.schema.sequence.create('message_id_sequence', {if_not_exists = true})

box.schema.space.create('counter', {if_not_exists = true})
box.space.counter:format({
    {name = 'id', type = 'unsigned'},
    {name = 'current_user_id', type = 'unsigned'},
    {name = 'user_id', type = 'unsigned'},
    {name = 'count', type = 'unsigned'},
})
box.space.counter:create_index('counter_idx', {type = 'tree', parts = {'id'}, if_not_exists = true})
box.space.counter:create_index('counter_current_user_id_idx', {type = 'tree', parts = {'current_user_id'}, if_not_exists = true, unique = false})
box.space.counter:create_index('counter_comp_idx', {type = 'tree', parts = {'current_user_id', 'user_id'}, if_not_exists = true, unique = false})
box.schema.sequence.create('counter_id_sequence', {if_not_exists = true})

function connected()
    print('Connected')
end

function get_dialog_id(currentUserId, userId)
    return box.space.dialog.index.dialog_comp_idx:select {currentUserId, userId}
end

function create_dialog(currentUserId, userId)
    box.space.dialog:insert{box.sequence.dialog_id_sequence:next(), currentUserId, userId}
end

function get_message(fromId, toId)
    return box.space.message.index.message_from_to_comp_idx:select {fromId, toId}
end

function get_messages(dialogId)
    return box.space.message.index.message_comp_idx:select {dialogId}
end

function create_message(fromId, toId, text, dialogId)
    box.space.message:insert{box.sequence.message_id_sequence:next(), fromId, toId, text, dialogId}
end

function delete_message(id)
    box.space.message:insert{id}
end

function get_counters(currentUserId)
    return box.space.counter.index.counter_current_user_id_idx:select {currentUserId}
end

function get_counter(currentUserId, userId)
    return box.space.counter.index.counter_comp_idx:select {currentUserId, userId}
end

function create_counter(currentUserId, userId, count)
    box.space.counter:insert{box.sequence.counter_id_sequence:next(), currentUserId, userId, count}
end

function update_counter(id, currentUserId, userId, count)
    box.space.counter:replace{id, currentUserId, userId, count}
end

function delete_counter(id)
    box.space.counter:delete{id}
end

connected()