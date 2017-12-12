create table Acessorios (id  bigserial not null, descricao varchar(255), primary key (id))
create table Carro (id  bigserial not null, descricao varchar(255), primary key (id))
create table Carro_Acessorios (Carro_id int8 not null, acessorios_id int8 not null)
create table Usuario (id  bigserial not null, cadastro TIMESTAMP DEFAULT NOW() not null, celular varchar(15), confirmado boolean, descricao varchar(20) not null, email varchar(255) not null, senha varchar(128) not null, tipo varchar(10), validade date, usuarioPai_id int8, primary key (id))
create table ctr_baixa_pagamento (id  bigserial not null, data_pagamento TIMESTAMP DEFAULT NOW() not null, lancamento TIMESTAMP DEFAULT NOW() not null, valor numeric(19, 2), debito_id int8 not null, usuario_id int8 not null, primary key (id))
create table ctr_baixa_recebimento (id  bigserial not null, data_pagamento date, lancamento TIMESTAMP DEFAULT NOW() not null, valor numeric(19, 2), credito_id int8 not null, usuario_id int8 not null, primary key (id))
create table ctr_banco (id  bigserial not null, descricao varchar(30) not null, usuario_id int8 not null, primary key (id))
create table ctr_bandeira_cartao (id  bigserial not null, arquivo oid, descricao varchar(15) not null, tipoArquivo varchar(255), usuario_id int8 not null, primary key (id))
create table ctr_cartao_credito (id  bigserial not null, descricao varchar(30) not null, dia_vencimento int4 not null, dias_fechamento int4 not null, digitos_finais int4, bandeira_id int8, usuario_id int8 not null, primary key (id))
create table ctr_categoria_despesa (id  bigserial not null, descricao varchar(30), usuario_id int8 not null, primary key (id))
create table ctr_cheque (id  bigserial not null, descricao varchar(30), banco_id int8 not null, usuario_id int8 not null, primary key (id))
create table ctr_conta_pagar (id int8 not null, data_lancamento TIMESTAMP DEFAULT NOW() not null, descricao varchar(255), parcela_total int4, periodo_pagamento int4, situacao varchar(20), valor_total numeric(19, 2) not null, vencimento date not null, usuario_id int8 not null, tipo_pagamento varchar(255), categoria_id int8 not null, primary key (id))
create table ctr_conta_receber (id int8 not null, data_lancamento TIMESTAMP DEFAULT NOW() not null, descricao varchar(255), parcela_total int4, periodo_pagamento int4, situacao varchar(20), valor_total numeric(19, 2) not null, vencimento date not null, usuario_id int8 not null, tipo_recebimento varchar(255), devedor_id int8 not null, primary key (id))
create table ctr_credito_cheque (id int8 not null, descricao varchar(255) not null, lancamento TIMESTAMP DEFAULT NOW() not null, parcela int4, parcela_total int4, situacao_pagamento varchar(255), tipo_recebimento varchar(255), valor numeric(19, 2) not null, vencimento date, conta_id int8 not null, devedor_id int8 not null, usuario_id int8 not null, numeroCheque int8, primary key (id))
create table ctr_credito_dinheiro (id int8 not null, descricao varchar(255) not null, lancamento TIMESTAMP DEFAULT NOW() not null, parcela int4, parcela_total int4, situacao_pagamento varchar(255), tipo_recebimento varchar(255), valor numeric(19, 2) not null, vencimento date, conta_id int8 not null, devedor_id int8 not null, usuario_id int8 not null, primary key (id))
create table ctr_debito_cartao (id int8 not null, descricao varchar(255) not null, lancamento TIMESTAMP DEFAULT NOW() not null, parcela int4, parcela_total int4, situacao_pagamento varchar(255), tipo_pagamento varchar(255), valor numeric(19, 2) not null, vencimento date, categoria_id int8 not null, conta_id int8 not null, usuario_id int8 not null, cartao_id int8 not null, primary key (id))
create table ctr_debito_cheque (id int8 not null, descricao varchar(255) not null, lancamento TIMESTAMP DEFAULT NOW() not null, parcela int4, parcela_total int4, situacao_pagamento varchar(255), tipo_pagamento varchar(255), valor numeric(19, 2) not null, vencimento date, categoria_id int8 not null, conta_id int8 not null, usuario_id int8 not null, numero_cheque int8 not null, cheque_id int8 not null, primary key (id))
create table ctr_debito_dinheiro (id int8 not null, descricao varchar(255) not null, lancamento TIMESTAMP DEFAULT NOW() not null, parcela int4, parcela_total int4, situacao_pagamento varchar(255), tipo_pagamento varchar(255), valor numeric(19, 2) not null, vencimento date, categoria_id int8 not null, conta_id int8 not null, usuario_id int8 not null, primary key (id))
create table ctr_despesa_mensal (id  bigserial not null, descricao varchar(40), dia_vencimento int4, valor numeric(19, 2), usuario_id int8 not null, primary key (id))
create table ctr_devedor (id  bigserial not null, celular varchar(15), descricao varchar(255), email varchar(40), fixo varchar(15), usuario_id int8 not null, primary key (id))
create table ctr_movimento (id  bigserial not null, data_lancamento TIMESTAMP DEFAULT NOW() not null, descricao varchar(50) not null, tipo_movimento varchar(255), valor numeric(19, 2), usuario_id int8 not null, primary key (id))
create table log_erro (id  bigserial not null, data TIMESTAMP DEFAULT NOW() not null, mensagem text, usuario_desc varchar(30), usuario_id int8, primary key (id))
create table parametros_sistema (id  bigserial not null, tema varchar(255), usuario_id int8 not null, primary key (id))
create table pdv_caixa (id  bigserial not null, data date, status varchar(255), valor_final numeric(19, 2), valor_inicial numeric(19, 2), usuario_id int8 not null, primary key (id))
create table pdv_caixa_fechamento (id  bigserial not null, data TIMESTAMP DEFAULT NOW() not null, valor numeric(19, 2), caixa_id int8 not null, recebimento_id int8 not null, usuario_id int8 not null, primary key (id))
create table pdv_caixa_movimento (id  bigserial not null, data TIMESTAMP DEFAULT NOW() not null, descricao varchar(50), tipo varchar(255), valor numeric(19, 2), caixa_id int8 not null, tipoRecebimento_id int8 not null, usuario_id int8 not null, primary key (id))
create table pdv_cliente (id  bigserial not null, celular varchar(14), nome varchar(50) not null, telefone varchar(14), usuario_id int8 not null, primary key (id))
create table pdv_estoque (id  bigserial not null, qtde INTEGER DEFAULT 0 not null, qtde_minima INTEGER DEFAULT 0 not null, produto_id int8 not null, usuario_id int8 not null, primary key (id))
create table pdv_fornecedor (id  bigserial not null, celular varchar(10), descricao varchar(50), telefone varchar(10), vendedor varchar(30), usuario_id int8 not null, primary key (id))
create table pdv_funcionario (id  bigserial not null, celular varchar(15), comissao float8, endereco varchar(255), nome varchar(50) not null, telefone varchar(15), usuario_id int8 not null, primary key (id))
create table pdv_historico_valor_produto (id  bigserial not null, data_lancamento TIMESTAMP DEFAULT NOW() not null, lucro float8 not null, valor_custo numeric(19, 2) not null, valor_venda numeric(19, 2) not null, produto_id int8 not null, usuario_id int8 not null, primary key (id))
create table pdv_item_venda (id  bigserial not null, qtde int4, valor_custo numeric(15, 2), valor_total numeric(15, 2), valor_unitario numeric(15, 2), produto_id int8 not null, orcamento_id int8, primary key (id))
create table pdv_movimento_estoque (id  bigserial not null, data TIMESTAMP DEFAULT NOW() not null, qtde int4, tipo_lancamento varchar(255), produto_id int8 not null, usuario_id int8 not null, primary key (id))
create table pdv_orcamento (id  bigserial not null, cobrar_tx_servico boolean, data_lancamento TIMESTAMP DEFAULT NOW() not null, observacao text, perc_tx_servico float8, status varchar(255) not null, valor_itens numeric(19, 2), valor_tx_servico numeric(19, 2), valor_total numeric(19, 2), cliente_id int8, funcionario_id int8, usuario_id int8 not null, primary key (id))
create table pdv_produto (id  bigserial not null, cod_barras varchar(48), descricao varchar(50), status varchar(255), unidade varchar(10), fornecedor_id int8 not null, usuario_id int8 not null, primary key (id))
create table pdv_tipo_recebimento (id  bigserial not null, descricao varchar(30) not null, usuario_id int8 not null, primary key (id))
create table pdv_valor_produto (id  bigserial not null, data_atualizacao TIMESTAMP DEFAULT NOW() not null, lucro_percentual float8 not null, valor_custo numeric(19, 2) not null, valor_venda numeric(19, 2) not null, produto_id int8 not null, usuario_id int8 not null, primary key (id))
create table pdv_venda (id  bigserial not null, data_lancamento TIMESTAMP DEFAULT NOW() not null, perc_desconto float8, valor_desconto numeric(19, 2), valor_total numeric(19, 2), orcamento_id int8, primary key (id))
create table pdv_venda_pagamento (id  bigserial not null, data_lancamento TIMESTAMP DEFAULT NOW() not null, valor numeric(19, 2) not null, tipoRecebimento_id int8, usuario_id int8 not null, venda_id int8, primary key (id))
create table tb_telefones (id  bigserial not null, numero varchar(255), tel_usuario int8, primary key (id))
create table tb_usuario (TIPO_USUARIO varchar(31) not null, id  bigserial not null, nome varchar(255), primary key (id))
alter table Usuario add constraint UK_ot4mkgalx5jiwecv8a9xtfc7f  unique (descricao)
alter table Usuario add constraint UK_4tdehxj7dh8ghfc68kbwbsbll  unique (email)
alter table pdv_estoque add constraint UK_6okej149qh36y80hhbgoqrhec  unique (produto_id)
alter table pdv_produto add constraint UK_ec55rljhhoj5tqp6x0uhdd6ys  unique (cod_barras)
alter table pdv_valor_produto add constraint UK_2ayqfciq2wqlddjydq4oggl53  unique (produto_id)
alter table Carro_Acessorios add constraint FK_t7420ixu0u0ponmh6bhc4rtrn foreign key (acessorios_id) references Acessorios
alter table Carro_Acessorios add constraint FK_om06ixabkxcfgt7hywrw58rb foreign key (Carro_id) references Carro
alter table Usuario add constraint FK_k9wmcafi3e2k04l2yqay4fw82 foreign key (usuarioPai_id) references Usuario
alter table ctr_baixa_pagamento add constraint FK_oljqddkmlk8b65s93l6ndya2b foreign key (usuario_id) references Usuario
alter table ctr_baixa_recebimento add constraint FK_c8meqy4ae2dp3l5oqs7cjotwj foreign key (usuario_id) references Usuario
alter table ctr_banco add constraint FK_5wn2vbxmbivs7px3v2f5vw7g9 foreign key (usuario_id) references Usuario
alter table ctr_bandeira_cartao add constraint FK_7rd7daucvgc5mcgc01h4xoa8q foreign key (usuario_id) references Usuario
alter table ctr_cartao_credito add constraint FK_taa9v9ecy7epgxqpeet2rsukx foreign key (bandeira_id) references ctr_bandeira_cartao
alter table ctr_cartao_credito add constraint FK_nfx246rxemaq4hqmprb9coji9 foreign key (usuario_id) references Usuario
alter table ctr_categoria_despesa add constraint FK_pbh23ukxq125vvw3925i7gffw foreign key (usuario_id) references Usuario
alter table ctr_cheque add constraint FK_2k5k6km2lj4v2nrrnaj35xf6 foreign key (banco_id) references ctr_banco
alter table ctr_cheque add constraint FK_i7cupqewbf8chc34hbd8pqeax foreign key (usuario_id) references Usuario
alter table ctr_conta_pagar add constraint FK_bae3x5q2o6uwrhgxthmsao6b7 foreign key (categoria_id) references ctr_categoria_despesa
alter table ctr_conta_pagar add constraint FK_qlpa5m0x2g26dt9w22vxe0a6g foreign key (usuario_id) references Usuario
alter table ctr_conta_receber add constraint FK_1ls3xtxbaudoy73ukxchrg8b4 foreign key (devedor_id) references ctr_devedor
alter table ctr_conta_receber add constraint FK_m9qq4urf7yk2w7h3u9wrheq1i foreign key (usuario_id) references Usuario
alter table ctr_credito_cheque add constraint FK_oi6mcakpmmmlm04axgirl0hxy foreign key (conta_id) references ctr_conta_receber
alter table ctr_credito_cheque add constraint FK_bv5dyp4rcfstk1ric5m1oubh3 foreign key (devedor_id) references ctr_devedor
alter table ctr_credito_cheque add constraint FK_h7w5rbuhei1us2vtatotlbjr2 foreign key (usuario_id) references Usuario
alter table ctr_credito_dinheiro add constraint FK_6nfiolmnufkwe7dou4a2p8g22 foreign key (conta_id) references ctr_conta_receber
alter table ctr_credito_dinheiro add constraint FK_6c1ub8nvvf8rdu19y4srnk5hs foreign key (devedor_id) references ctr_devedor
alter table ctr_credito_dinheiro add constraint FK_7x5125c53v768g8oqqol0boy3 foreign key (usuario_id) references Usuario
alter table ctr_debito_cartao add constraint FK_m99tg3o13dxaq93ivk5gukh4g foreign key (cartao_id) references ctr_cartao_credito
alter table ctr_debito_cartao add constraint FK_s21woa7l1gygm1gtnoahn2ko8 foreign key (categoria_id) references ctr_categoria_despesa
alter table ctr_debito_cartao add constraint FK_q6xipk6y22q0i8spxud0x3v56 foreign key (conta_id) references ctr_conta_pagar
alter table ctr_debito_cartao add constraint FK_nyvjpyv55vwhiiieloc434nx4 foreign key (usuario_id) references Usuario
alter table ctr_debito_cheque add constraint FK_6lgji6oe8gbm36ckodoya4r0 foreign key (cheque_id) references ctr_cheque
alter table ctr_debito_cheque add constraint FK_9u42hs6ot44g6px01duxym9yu foreign key (categoria_id) references ctr_categoria_despesa
alter table ctr_debito_cheque add constraint FK_mea4bvqxnnhfrv6iykh26rkg0 foreign key (conta_id) references ctr_conta_pagar
alter table ctr_debito_cheque add constraint FK_df4wpoopod9j1ejtn202c9i8u foreign key (usuario_id) references Usuario
alter table ctr_debito_dinheiro add constraint FK_soymqbj64vbl7356c29iu3aij foreign key (categoria_id) references ctr_categoria_despesa
alter table ctr_debito_dinheiro add constraint FK_dcwyktsvi9getjpswurdb39vu foreign key (conta_id) references ctr_conta_pagar
alter table ctr_debito_dinheiro add constraint FK_2qdnusr89asyp70j2dfm038hx foreign key (usuario_id) references Usuario
alter table ctr_despesa_mensal add constraint FK_mm8dmu333l5rmqgloi1j1mr9c foreign key (usuario_id) references Usuario
alter table ctr_devedor add constraint FK_frm0u2mn0gyu99wdymk9633eb foreign key (usuario_id) references Usuario
alter table ctr_movimento add constraint FK_sl9qyit6pg34wiaflicl1fsgm foreign key (usuario_id) references Usuario
alter table parametros_sistema add constraint FK_rqhusk680njtmrhg9bme6gsca foreign key (usuario_id) references Usuario
alter table pdv_caixa add constraint FK_9rloxp4qy2f7q26niu3hpaxga foreign key (usuario_id) references Usuario
alter table pdv_caixa_fechamento add constraint FK_pol8ix37dvtf7o9691ggi1rtd foreign key (caixa_id) references pdv_caixa
alter table pdv_caixa_fechamento add constraint FK_ivetmswjv9clkyfqjag2l5np7 foreign key (recebimento_id) references pdv_tipo_recebimento
alter table pdv_caixa_fechamento add constraint FK_kjbr8tg14sj48elob6qiyfnuf foreign key (usuario_id) references Usuario
alter table pdv_caixa_movimento add constraint FK_rejaax6e6ect7ojnfvaopdiy3 foreign key (caixa_id) references pdv_caixa
alter table pdv_caixa_movimento add constraint FK_nmuyln6ni0oewijiss1t0yffy foreign key (tipoRecebimento_id) references pdv_tipo_recebimento
alter table pdv_caixa_movimento add constraint FK_q1edscwgiv669ewtl0ga8q7uk foreign key (usuario_id) references Usuario
alter table pdv_cliente add constraint FK_i0qavjnnt1mdxf2f6on4skgmi foreign key (usuario_id) references Usuario
alter table pdv_estoque add constraint FK_6okej149qh36y80hhbgoqrhec foreign key (produto_id) references pdv_produto
alter table pdv_estoque add constraint FK_l84rm2nmc060beo48uarn1bp6 foreign key (usuario_id) references Usuario
alter table pdv_fornecedor add constraint FK_3r7onu95sw5nbv186motv61bx foreign key (usuario_id) references Usuario
alter table pdv_funcionario add constraint FK_1getnkr0e7vp2ugqu22sql4ui foreign key (usuario_id) references Usuario
alter table pdv_historico_valor_produto add constraint FK_61eknryg9k619cnj40ouy7cpg foreign key (produto_id) references pdv_produto
alter table pdv_historico_valor_produto add constraint FK_3vdomdl65451acw1pnipa3qb3 foreign key (usuario_id) references Usuario
alter table pdv_item_venda add constraint FK_o6f5xy39n9acxpoxdw8jgk9bw foreign key (produto_id) references pdv_produto
alter table pdv_item_venda add constraint FK_ewyxvfc0puatdx9nfwbs0k4ws foreign key (orcamento_id) references pdv_orcamento
alter table pdv_movimento_estoque add constraint FK_5gt4w697rrs6sq1eyla2ua7jo foreign key (produto_id) references pdv_produto
alter table pdv_movimento_estoque add constraint FK_mi8ymeu8x1gpsoe80dtryc7vm foreign key (usuario_id) references Usuario
alter table pdv_orcamento add constraint FK_6btukwaapjwjpl86nnrye9s82 foreign key (cliente_id) references pdv_cliente
alter table pdv_orcamento add constraint FK_bqnh4rbbj6xhql91vkjya37m9 foreign key (funcionario_id) references pdv_funcionario
alter table pdv_orcamento add constraint FK_9wrjxkdm9s3m8o02nhgm1mj5m foreign key (usuario_id) references Usuario
alter table pdv_produto add constraint FK_7rf7p3kgo78d7b3xkm9tsacnq foreign key (fornecedor_id) references pdv_fornecedor
alter table pdv_produto add constraint FK_7298xb4mc3nr9ju8ps7l1t69h foreign key (usuario_id) references Usuario
alter table pdv_tipo_recebimento add constraint FK_76fsbvmbxr7enfn06p3p97k2e foreign key (usuario_id) references Usuario
alter table pdv_valor_produto add constraint FK_2ayqfciq2wqlddjydq4oggl53 foreign key (produto_id) references pdv_produto
alter table pdv_valor_produto add constraint FK_ncw3i5cfu8yvb8jfmax3as6y5 foreign key (usuario_id) references Usuario
alter table pdv_venda add constraint FK_cp8o5ss3m2debxn2qh0y94tis foreign key (orcamento_id) references pdv_orcamento
alter table pdv_venda_pagamento add constraint FK_d35lfkvjqutitrhhlsdaltry8 foreign key (tipoRecebimento_id) references pdv_tipo_recebimento
alter table pdv_venda_pagamento add constraint FK_3oufb6s2eow2clpvge2ljmt1g foreign key (usuario_id) references Usuario
alter table pdv_venda_pagamento add constraint FK_fl7fom1t4x5jlrt1a5upih16e foreign key (venda_id) references pdv_venda
alter table tb_telefones add constraint FK_kl7hjt65kx1v9nkm69uwmukcc foreign key (tel_usuario) references tb_usuario
alter table tb_telefones add constraint FK_kl7hjt65kx1v9nkm69uwmukcc foreign key (tel_usuario) references tb_usuario
create sequence conta_id_seq
create sequence credito_id_seq
create sequence debito_id_seq
