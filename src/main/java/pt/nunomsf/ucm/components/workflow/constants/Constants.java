package pt.nunomsf.ucm.components.workflow.constants;

public class Constants {

   public class ResultSets {
       public final static String DOCINFO = "QdocInfo";
   }
   public class Fields {
       public final static String xIdcProfile = "xIdcProfile";
       public final static String dID = "dID";
       public final static String dDocName = "dDocName";
       public final static String dOriginalName = "dOriginalName";
       public final static String dUser = "dUser";
       public final static String xNumDocumento = "xNumDocumento";
       public final static String xCodNifCliente = "xCodNifCliente";
   }

   public final class DocRevisionAttribute {
       public final static String isPrimary = "isPrimary";
       public final static String isWebformat = "isWebformat";
       public final static String rendition = "rendition";
       public final static String file = "file";
   }

   public final class Queries {
       public final static String QGetFileByDid = "QGetFileByDid";

       public final class IARApproveDocumentAudit {
           public final static String name = "IARApproveDocumentAudit";
           public final class Collumns {
               public final static String dID = "dID";
               public final static String numFatura = "numFatura";
               public final static String nifCliente = "nifCliente";
               public final static String nomDocumento = "nomDocumento";
               public final static String codUtilizador = "codUtilizador";
               public final static String datCriacao = "datCriacao";
               public final static String codStatus = "codStatus";
           }
       };
       public final class UARApproveDocumentAudit {
           public final static String name = "UARApproveDocumentAudit";
           public final class Collumns {
               public final static String dID = "dID";
               public final static String codStatus = "codStatus";
               public final static String codResposta = "codResposta";
               public final static String txtMessage = "txtMessage";
               public final static String datAtualizacao = "datAtualizacao";
           }
       };
   }

}
