const User = require('../models/User');

module.exports = {

    async index(req, res) {
        const users = await User.findAll();
    
        return res.json(users);
    },

    async store(req, res) {
        const { 
            name, 
            email, 
            confirm_email, 
            phone,
            password, 
            confirm_password 
        } = req.body;

        const userExist = await User.findOne({ where: { email } });

        if(userExist) {
            return res.status(400).send({
                message: 'Este e-mail já estar cadastrado!'
            });
        } 
    
        const user = await User.create({
            name, 
            email, 
            confirm_email, 
            phone, 
            password, 
            confirm_password
        });
    
        return res.json(user);
    },

    async update(req, res) {

        const { 
            name, 
            email, 
            confirm_email, 
            phone, 
            password, 
            confirm_password } = req.body;

        const { user_id } = req.params;

        await User.update({
            name, 
            email, 
            confirm_email, 
            phone, 
            password, 
            confirm_password
        }, {
            where: {
                id: user_id
            }
        });

        return res.status(200).send({
            message: "Usuário atualizado com sucesso!",
        });

    },

    async delete(req, res) {

        const { user_id } = req.params;

        await User.destroy({
            where: {
                id: user_id
            }
        });

        return res.status(200).send({
            message: "Usuário deletado com sucesso!",
        });

    }
}