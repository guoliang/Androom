var Webpack = require('webpack');
var Path = require('path');
var ExtractTextPlugin = require('extract-text-webpack-plugin');

var BUILD_DIR = Path.resolve(__dirname, 'static');
var APP_DIR = Path.resolve(__dirname, 'reactjs');
var SASS_DIR = Path.resolve(__dirname, 'sass');

var config = {
    entry : {
        bundle:     [APP_DIR + '/main.js'],            //react
        bootstrap:  ['bootstrap-loader/extractStyles'],
        index:      [SASS_DIR + '/index.scss']
    },
    output : {
        path : BUILD_DIR + '/js/',
        filename : '[name].js'
    },
    module : {
        loaders : [
            //REACT JS
            {
                test : /.jsx?$/,
                loader : 'babel-loader',
                exclude : /node-modules/,
                query : {
                    presets : [ 'es2015', 'react' ],
                    compact: false //not include superfluous whitespace characters and line terminators
                }
            },
            //SASS
            {
                test: /\.scss$/,
                loader: ExtractTextPlugin.extract( ['css', 'sass'] )
            },
            //BOOTSTRAP SPECIFIC
            { test: /\.(woff2?|svg)$/, loader: 'url?limit=10000' },
            { test: /\.(ttf|eot)$/, loader: 'file' }
        ]
    },
    plugins: [
        new ExtractTextPlugin ('../css/[name].css', { allChunks: true }),
        //new Webpack.optimize.UglifyJsPlugin ({minimize: true}),
        new Webpack.ProvidePlugin({
            $: "jquery",
            jQuery: "jquery",
            "window.jQuery": "jquery",
            noty: "noty"
        })
    ]
};

module.exports = config;