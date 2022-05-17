package pt.nunomsf.ucm.components.workflow.constants;

public class Constants {
    public static class Tables {
        //Tables
        public static class DocMeta {

            public static String name() {return "DocMeta";}

            public final static Column dID = Column.of("dID");
            public final static Column xIdcProfile = Column.of("xIdcProfile");
            public final static Column xNumDocumento = Column.of("xNumDocumento");
            public final static Column xCodNifCliente = Column.of("xCodNifCliente");
            public final static Column xClasseProjeto = Column.of("xClasseProjeto");
        }
        public static class Revisions {
            public static String name() {return "Revisions";}
            public final static Column dID = Column.of("dID");
            public final static Column dDocName = Column.of("dDocName");
        }
        public static class WorkflowHistory {
            public static String name() {return "WorkflowHistory";}
            public final static Column dWfName = Column.of("dWfName");
            public final static Column dWfStepName = Column.of("dWfStepName");
            public final static Column dAction = Column.of("dAction");
            public final static Column dUser = Column.of("dUser");
        }
        public static class Documents {
            public static String name() {return "Documents";}
            public final static Column isPrimary = Column.of("isPrimary");
            public final static Column isWebformat = Column.of("isWebformat");
            public final static Column dOriginalName = Column.of("dOriginalName");
        }
        public static class AMT_AR_APPROVE_EBS_INTEGRATION {
            public static String name() {return "AMT_AR_APPROVE_EBS_INTEGRATION";}
            public final static Column dID = Column.of("dID");
            public final static Column numFatura = Column.of("numFatura");
            public final static Column nifCliente = Column.of("nifCliente");
            public final static Column nomDocumento = Column.of("nomDocumento");
            public final static Column codUtilizador = Column.of("codUtilizador");
            public final static Column datCriacao = Column.of("datCriacao");
            public final static Column codStatus = Column.of("codStatus");
            public final static Column codResposta = Column.of("codResposta");
            public final static Column txtMessage = Column.of("txtMessage");
            public final static Column datAtualizacao = Column.of("datAtualizacao");

        }
        //Procedures
        public static class GET_FILE {
            public static String name() {return "GET_FILE";}
            public final static Column dID = Tables.Revisions.dID;
            public final static Column isPrimary = Tables.Documents.isPrimary;
            public final static Column isWebformat = Tables.Documents.isWebformat;
            public final static Column rendition = Column.of("rendition");
            public final static Column file = Column.of("file");
        }
    }
    public static class Queries {

        public static class QGetFileByDid {
            public static String name() {return "QGetFileByDid";}
            public static class Columns {
                public final static Column dID = Tables.GET_FILE.dID;
                public final static Column isPrimary = Tables.GET_FILE.isPrimary;
                public final static Column isWebformat = Tables.GET_FILE.isWebformat;
                public final static Column rendition = Tables.GET_FILE.rendition;
                public final static Column file = Tables.GET_FILE.file;
            }
        }

        public static class QdocInfo {
            public static String name() {return "QdocInfo";}
            public final static class Columns {
                public final static Column dID = Tables.Revisions.dID;
                public final static Column xNumDocumento = Tables.DocMeta.xNumDocumento;
                public final static Column xCodNifCliente = Tables.DocMeta.xCodNifCliente;
                public final static Column xIdcProfile = Tables.DocMeta.xIdcProfile;
                public final static Column xClasseProjeto = Tables.DocMeta.xClasseProjeto;
            }
        }

        public static class IARApproveDocumentAudit {
            public static String name() {return "IARApproveDocumentAudit";}
            public final static class Columns {
                public final static Column dID = Tables.AMT_AR_APPROVE_EBS_INTEGRATION.dID;
                public final static Column numFatura = Tables.AMT_AR_APPROVE_EBS_INTEGRATION.numFatura;
                public final static Column nifCliente = Tables.AMT_AR_APPROVE_EBS_INTEGRATION.nifCliente;
                public final static Column nomDocumento = Tables.AMT_AR_APPROVE_EBS_INTEGRATION.nomDocumento;
                public final static Column codUtilizador = Tables.AMT_AR_APPROVE_EBS_INTEGRATION.codUtilizador;
                public final static Column datCriacao = Tables.AMT_AR_APPROVE_EBS_INTEGRATION.datCriacao;
                public final static Column codStatus = Tables.AMT_AR_APPROVE_EBS_INTEGRATION.codStatus;
            }
        }

        public static class UARApproveDocumentAudit {
            public static String name() {return "UARApproveDocumentAudit";}
            public final static class Columns {
                public final static Column dID = Tables.AMT_AR_APPROVE_EBS_INTEGRATION.dID;
                public final static Column numFatura = Tables.AMT_AR_APPROVE_EBS_INTEGRATION.numFatura;
                public final static Column nifCliente = Tables.AMT_AR_APPROVE_EBS_INTEGRATION.nifCliente;
                public final static Column nomDocumento = Tables.AMT_AR_APPROVE_EBS_INTEGRATION.nomDocumento;
                public final static Column codUtilizador = Tables.AMT_AR_APPROVE_EBS_INTEGRATION.codUtilizador;
                public final static Column datCriacao = Tables.AMT_AR_APPROVE_EBS_INTEGRATION.datCriacao;
                public final static Column codStatus = Tables.AMT_AR_APPROVE_EBS_INTEGRATION.codStatus;

                public final static Column codResposta = Tables.AMT_AR_APPROVE_EBS_INTEGRATION.codResposta;
                public final static Column txtMessage = Tables.AMT_AR_APPROVE_EBS_INTEGRATION.txtMessage;
                public final static Column datAtualizacao = Tables.AMT_AR_APPROVE_EBS_INTEGRATION.datAtualizacao;
            }
        }
    }

}


