package com.reandroid.apkeditor;

import com.reandroid.commons.command.ARGException;

import java.io.File;

public class Options {
    public File inputFile;
    public File outputFile;
    public boolean force;
    public boolean validateResDir;
    public String resDirName;

    public void parse(String[] args) throws ARGException {
        parseResDirName(args);
        parseForce(args);
        parseValidateResDir(args);
        checkUnknownOptions(args);
    }
    private void parseValidateResDir(String[] args) throws ARGException {
        validateResDir=containsArg(ARG_validate_res_dir, true, args);
    }
    private void parseResDirName(String[] args) throws ARGException {
        this.resDirName=parseArgValue(ARG_resDir, true, args);
    }
    private void parseForce(String[] args) throws ARGException {
        force=containsArg(ARG_force, true, args);
    }
    protected void checkUnknownOptions(String[] args) throws ARGException {
        args=Util.trimNull(args);
        if(Util.isEmpty(args)){
            return;
        }
        throw new ARGException("Unknown option: "+args[0]);
    }
    protected String parseArgValue(String argSwitch, boolean ignore_case, String[] args) throws ARGException {
        if(ignore_case){
            argSwitch=argSwitch.toLowerCase();
        }
        int max=args.length;
        for(int i=0;i<max;i++){
            String s=args[i];
            if(s==null){
                continue;
            }
            s=s.trim();
            String tmpArg=s;
            if(ignore_case){
                tmpArg=tmpArg.toLowerCase();
            }
            if(tmpArg.equals(argSwitch)){
                int i2=i+1;
                if(i2>=max){
                    throw new ARGException("Missing value near: \""+s+"\"");
                }
                String value=args[i2];
                if(Util.isEmpty(value)){
                    throw new ARGException("Missing value near: \""+s+"\"");
                }
                value=value.trim();
                args[i]=null;
                args[i2]=null;
                return value;
            }
        }
        return null;
    }
    protected File parseFile(String argSwitch, String[] args) throws ARGException {
        int max=args.length;
        for(int i=0;i<max;i++){
            String s=args[i];
            if(s==null){
                continue;
            }
            s=s.trim();
            if(s.equals(argSwitch)){
                int i2=i+1;
                if(i2>=max){
                    throw new ARGException("Missing path near: \""+argSwitch+"\"");
                }
                String path=args[i2];
                if(Util.isEmpty(path)){
                    throw new ARGException("Missing path near: \""+argSwitch+"\"");
                }
                path=path.trim();
                args[i]=null;
                args[i2]=null;
                return new File(path);
            }
        }
        return null;
    }
    protected boolean containsArg(String argSwitch, boolean ignore_case, String[] args) throws ARGException {
        if(ignore_case){
            argSwitch=argSwitch.toLowerCase();
        }
        int max=args.length;
        for(int i=0;i<max;i++){
            String s=args[i];
            if(s==null){
                continue;
            }
            s=s.trim();
            if(ignore_case){
                s=s.toLowerCase();
            }
            if(s.equals(argSwitch)){
                args[i]=null;
                return true;
            }
        }
        return false;
    }

    protected static final String ARG_output="-o";
    protected static final String ARG_DESC_output="output path";
    protected static final String ARG_input="-i";
    protected static final String ARG_DESC_input="input path";
    protected static final String ARG_resDir="-res-dir";
    protected static final String ARG_DESC_resDir="sets resource files root dir name\n(eg. for obfuscation to move files from 'res/*' to 'r/*' or vice versa)";
    protected static final String ARG_validate_res_dir="-vrd";
    protected static final String ARG_DESC_validate_res_dir="validate resources dir name\n(eg. if a drawable resource file path is 'res/abc.png' then it\nmoves to 'res/drawable/abc.png')";
    protected static final String ARG_force="-f";
    protected static final String ARG_DESC_force="force delete output path";
}
