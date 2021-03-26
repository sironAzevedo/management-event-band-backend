const Band = require('../models/Band');
const User = require('../models/User');

module.exports = {
    async index(req, res) {
        const { user_id } = req.params;

        const user = await User.findByPk(user_id, {
          include: {
            association: 'bands', 
            attributes: ['name'], 
            through: {
              attributes: []
            } 
          }
        })
    
        return res.json(user.bands);
    },
    
    async store(req, res) {

        const { user_id } = req.params;
        const { name } = req.body;

        const user = await User.findByPk(user_id);

        if(!user) {
            return res.status(400).json({ error: 'User not found' });
        };

        const [ band ] = await Band.findOrCreate({
            where: { name }
        });

        await user.addBand(band);
        return res.json(band);
    },

    async delete(req, res) {
        const { user_id } = req.params;
        const { name } = req.body;

        const user = await User.findByPk(user_id);

        if (!user) {
            return res.status(400).json({ error: 'User not found' });
        }

        const band = await Band.findOne({
            where: { name }
        });

        await user.removeBand(band);

        return res.json();
    }   
}