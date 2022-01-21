// Vue3 관련 설정 파일
module.exports = {
  devServer: {
    https: false,
    port: 8083,
    open: true,
    proxy: {
      '/api/v1': {
        target: 'http://localhost:8080/'
      }
    },
    historyApiFallback: true,
    hot: true
  },
  css: {
    loaderOptions: {
      postcss: {
        ident: 'postcss',
        plugins: [require('tailwindcss')],
      },
    },
  },
  lintOnSave: false,
  outputDir: '../backend/src/main/resources/dist'
}
