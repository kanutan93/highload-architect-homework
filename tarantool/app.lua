box.cfg{}

local dialog_space = box.schema.space.create('dialog', {if_not_exists = true})
dialog_space:format({
    {name = 'id', type = 'unsigned'},
    {name = 'user1_id', type = 'unsigned'},
    {name = 'user2_id', type = 'unsigned'},
    {name = 'created_at', type = 'unsigned'},
})
dialog_space:create_index('dialog_idx', {type = 'tree', parts = {'id'}}, {if_not_exists = true})
local dialog_id_seq = box.schema.sequence.create('dialog_id_seq')

local message_space = box.schema.space.create('message', {if_not_exists = true})
message_space:format({
    {name = 'id', type = 'unsigned'},
    {name = 'from_id', type = 'unsigned'},
    {name = 'to_id', type = 'unsigned'},
    {name = 'text', type = 'string'},
    {name = 'dialog_id', type = 'unsigned'},
    {name = 'created_at', type = 'unsigned'},
})
message_space:create_index('message_idx', {type = 'tree', parts = {'id', 'dialog_id'}}, {if_not_exists = true})
local message_id_seq = box.schema.sequence.create('message_id_seq', {if_not_exists = true})

local os_date = require('os').date
local os_time = require('os').time

function createDialog(currentUserId, userId)
    dialog_space.insert(dialog_id_seq:next(), currentUserId, userId, os_date('%Y-%m-%dT%H:%M:%S%z', os_time()))
end