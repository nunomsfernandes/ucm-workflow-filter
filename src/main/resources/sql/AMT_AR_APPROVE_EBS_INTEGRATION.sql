--------------------------------------------------------
--  DDL for Table AMT_AR_APPROVE_EBS_INTEGRATION
--------------------------------------------------------

--------------------------------------------------------
--  DDL for Table AMT_AR_APPROVE_EBS_INTEGRATION
--------------------------------------------------------
CREATE TABLE "AMT_AR_APPROVE_EBS_INTEGRATION"
(
    "DID"             NUMBER,
    "DDOCNAME"        VARCHAR2(100 BYTE),
    "NUM_FATURA"      VARCHAR2(100 BYTE),
    "NIF_CLIENTE"     VARCHAR2(50 BYTE),
    "NOM_DOCUMENTO"   VARCHAR2(200 BYTE),
    "COD_RESPOSTA"    NUMBER,
    "DAT_CRIACAO"     DATE,
    "DAT_ATUALIZACAO" DATE,
    "COD_STATUS"      NUMBER,
    "COD_UTILIZADOR"  VARCHAR2(100 BYTE),
    "TXT_MESSAGE"     VARCHAR2(500 BYTE)
);

--------------------------------------------------------
--  DDL for Index AMT_AR_APPROVE_EBS_INTEGR_IDX3
--------------------------------------------------------
CREATE INDEX "AMT_AR_APPROVE_EBS_INTEGR_IDX3" ON "AMT_AR_APPROVE_EBS_INTEGRATION" ("NIF_CLIENTE");

--------------------------------------------------------
--  DDL for Index AMT_AR_APPROVE_EBS_INTEGR_IDX1
--------------------------------------------------------
CREATE INDEX "AMT_AR_APPROVE_EBS_INTEGR_IDX1" ON "AMT_AR_APPROVE_EBS_INTEGRATION" ("DDOCNAME");

--------------------------------------------------------
--  DDL for Index AMT_AR_APPROVE_EBS_INTEGR_IDX2
--------------------------------------------------------
CREATE INDEX "AMT_AR_APPROVE_EBS_INTEGR_IDX2" ON "AMT_AR_APPROVE_EBS_INTEGRATION" ("NUM_FATURA", "NIF_CLIENTE");

--------------------------------------------------------
--  DDL for Index AMT_AR_APPROVE_EBS_INTEGRA_PK
--------------------------------------------------------
CREATE UNIQUE INDEX "AMT_AR_APPROVE_EBS_INTEGRA_PK" ON "AMT_AR_APPROVE_EBS_INTEGRATION" ("DID");

--------------------------------------------------------
--  Constraints for Table AMT_AR_APPROVE_EBS_INTEGRATION
--------------------------------------------------------
ALTER TABLE "AMT_AR_APPROVE_EBS_INTEGRATION"
    MODIFY ("DID" NOT NULL ENABLE);
ALTER TABLE "AMT_AR_APPROVE_EBS_INTEGRATION"
    MODIFY ("DDOCNAME" NOT NULL ENABLE);
ALTER TABLE "AMT_AR_APPROVE_EBS_INTEGRATION"
    MODIFY ("NUM_FATURA" NOT NULL ENABLE);
ALTER TABLE "AMT_AR_APPROVE_EBS_INTEGRATION"
    MODIFY ("NIF_CLIENTE" NOT NULL ENABLE);
ALTER TABLE "AMT_AR_APPROVE_EBS_INTEGRATION"
    MODIFY ("COD_UTILIZADOR" NOT NULL ENABLE);
ALTER TABLE "AMT_AR_APPROVE_EBS_INTEGRATION"
    MODIFY ("NOM_DOCUMENTO" NOT NULL ENABLE);
ALTER TABLE "AMT_AR_APPROVE_EBS_INTEGRATION"
    MODIFY ("DAT_CRIACAO" NOT NULL ENABLE);
ALTER TABLE "AMT_AR_APPROVE_EBS_INTEGRATION"
    MODIFY ("COD_STATUS" NOT NULL ENABLE);
ALTER TABLE "AMT_AR_APPROVE_EBS_INTEGRATION"
    ADD CONSTRAINT "AMT_AR_APPROVE_EBS_INTEGRA_PK" PRIMARY KEY ("DID");

