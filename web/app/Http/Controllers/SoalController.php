<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Kreait\Firebase\Contract\Database;

class SoalController extends Controller
{
    protected $database;
    protected $tablename;
    protected $mata_kuliah;
    public function __construct(Database $database)
    {
        $this->database = $database;
        $this->tablename = 'test';
        $this->mata_kuliah ='mata_kuliah';
    }
    public function index($test_code) {
        $test = $this->database->getReference($this->tablename . '/' . $test_code)->getValue();
        $soals = $this->database->getReference($this->tablename . '/' . $test_code. '/soal')->getValue();
        return view('soal',compact('test','soals'));
    }

    public function create(Request $request)
    {
        $soal_code = str_shuffle(bin2hex(random_bytes(2)));
        
        $newData = $this->database->getReference($this->tablename . '/' . $request->code . '/soal' . '/' . $soal_code)->set([
            'soal_code' => $soal_code,
            'test_code' => $request->code,
            'pertanyaan' => $request->pertanyaan,
        ]);
    
        return redirect()->back();
    }
}
